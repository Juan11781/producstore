package com.sales.order_service.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.order_service.client.ProductClient;
import com.sales.order_service.dto.NotificationMessage;
import com.sales.order_service.dto.OrderResponseDto;
import com.sales.order_service.dto.ProductDto;
import com.sales.order_service.entity.Order;
import com.sales.order_service.entity.OrderedProduct;
import com.sales.order_service.repository.OrderRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.core.HttpHeaders;

@Tag(name="Order Controller", description="Operaciones relacionadas con la gestion de ordenes")
@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderRepository orderRepository;
	private final ProductClient productClient;
	private KafkaTemplate<String,NotificationMessage>kafkaTemplate;

	
	
	public OrderController(OrderRepository orderRepository, ProductClient productClient,
			KafkaTemplate<String, NotificationMessage> kafkaTemplate) {
		super();
		this.orderRepository = orderRepository;
		this.productClient = productClient;
		this.kafkaTemplate = kafkaTemplate;
	}
	
	@GetMapping("/send")
	public String sendTestMessage() {
		NotificationMessage message = new NotificationMessage(
					"test@example.com",
					"prueba desde order-service",
					"este es un correo de prueba",
				   "hora"
				);
		
	  kafkaTemplate.send("email-topic",message);
	  return "Mensaje enviado desde Order service";
	}
	
	@Operation(
			summary="Lista ordenes del usuario autenticado",
			description="Devuelve las ordernes por el usuario extraido desde el token JWT"
	)
	@ApiResponse(responseCode="200", description="Ordenes recuperadas correctamente")
	@GetMapping
	public List<OrderResponseDto>getOrders(
			@Parameter(description = "Token JWT del usuario", required= true)
			@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader){
		String userEmail = extractUserEmailFromToken(authHeader);
		
		List<Order> orders = orderRepository.findByUserEmail(userEmail);
		
		return orders.stream().map(order ->{
			
			List<ProductDto>productDetails = order.getProductIds().stream()
					.map(productId -> productClient.getProductById(productId, authHeader.substring(7)))
					.filter(p -> p != null)
					.toList();
			
			OrderResponseDto dto =  new OrderResponseDto();
			dto.setId(order.getId());
			dto.setUserEmail(order.getUserEmail());
			dto.setCreatedAt(order.getCreateAt());
			dto.setTotal(order.getTotal());
			dto.setProducts(productDetails);
			return dto;
			
		}).toList();
	}
	
	@Operation(
			summary="Crear una orden",
			description ="Crea una orden para el usuario autenticado validando el stock de productos"
			)
	@ApiResponse (responseCode = "200", description="Orden creada exitosamente")
	@ApiResponse(responseCode="400", description="Stock insuficiente o producto no encontrado")
	@PostMapping
	public Order createOrder(
			@RequestBody Order order,
			@Parameter(description ="Token JWT del usuario", required=true)
			@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader) {
		String userEmail   = extractUserEmailFromToken(authHeader);
		order.setUserEmail(userEmail);
		
		double total = 0.0;
		List<OrderedProduct>orderedProducts = new ArrayList<>();
		
		for(Long productId : order.getProductIds()) {
			
			if(!productClient.hasSufficientStock(productId, 1, authHeader.substring(7))) {
				throw new RuntimeException("Stock insuficiente para el producto ID: "+productId);
			}
			
			ProductDto product =productClient.getProductById(productId, authHeader.substring(7));
			if(product == null) {
				throw new RuntimeException("Producto con ID: "+productId+" no encontrado.");
			}
			
			orderedProducts.add(new OrderedProduct(
					product.getId(),
					product.getName(),
					product.getPrice()	
			));
			
			
			total += product.getPrice();
		}
		
	
		order.setTotal(total);
		order.setOrderedProducts(orderedProducts);
		 Order result = order = orderRepository.save(order);
		notificarConfirmacionOrden(order.getUserEmail());
		productClient.decreaseStock(order.getProductIds(), authHeader.substring(7));
		
		
		return result;
	}
	
	@Operation(
			summary = "Detalle de una orden especifica",
			description = "Devuelve los detalles de una orden si pertenece al usuario autenticado"
	)
	@ApiResponse(responseCode="200", description="Orden encontrada")
	@ApiResponse(responseCode="404", description="Orden no encontrada o acceso no autorizado")
	@GetMapping("/{id}")
	public OrderResponseDto getOrderById(
				@Parameter(description = "ID de la orden") @PathVariable Long id,
				@Parameter(description = "Token JWT del usuario", required= true)
				@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader
			) {
		
		String userEmail = extractUserEmailFromToken(authHeader);
		Order order  = orderRepository.findById(id)
					.orElseThrow(()-> new RuntimeException("Orden no encontrada"));
		
		if(!order.getUserEmail().equals(userEmail)) {
			throw new RuntimeException("Acceso denegado");
		}
		
		List<ProductDto> productDetails = order.getProductIds().stream()
				.map(productId -> productClient.getProductById(productId, authHeader.substring(7)))
				.filter(p->p!= null)
				.toList();
		
		OrderResponseDto dto = new OrderResponseDto();
		dto.setId(order.getId());
		dto.setUserEmail(order.getUserEmail());
		dto.setCreatedAt(order.getCreateAt());
		dto.setTotal(order.getTotal());
		dto.setProducts(productDetails);
		
		return dto;
	}
	
	public String extractUserEmailFromToken(String authHeader) {
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new RuntimeException("Token invalido");
		}
		
		String token = authHeader.substring(7);
		
		String [] chunks = token.split("\\.");
		if(chunks.length < 2)
		  throw new RuntimeException("Token malformado");
		
		String payload = new String(java.util.Base64.getUrlDecoder().decode(chunks[1]));
		String email = payload.split("\"sub\":\"")[1].split("\"")[0];
		
		return email;
		
	}
	
	
	private void notificarConfirmacionOrden(String userEmail) {
		NotificationMessage mensaje = new NotificationMessage();
		mensaje.setTo(userEmail);
		mensaje.setSubject("Confirmacion de compra");
		mensaje.setBody("Hola,\n\nTu orden ha sido procesada exitosamente.+"+getDate()
		+ "\nGracias por tu compra.\n\n- Equipo ProductStore");
		//mensaje.setFechaHoraCompra(getDate());
		System.out.println("Correo enviado atravez de kafka: "+userEmail);
		kafkaTemplate.send("email-topic", mensaje);
		System.out.println("Mensaje enviado a kafka: "+mensaje.getTo());

	}
	
	public static String getDate() {
		DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.now().format(formatter);	
	}
	
}
