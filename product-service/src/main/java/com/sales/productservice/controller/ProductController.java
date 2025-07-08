package com.sales.productservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.productservice.dto.StockRequest;
import com.sales.productservice.dto.StockUpdateRequest;
import com.sales.productservice.entity.Product;
import com.sales.productservice.entity.StockMovement;
import com.sales.productservice.repository.ProductRepository;
import com.sales.productservice.repository.StockMovementeRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private final ProductRepository productRepository;
	private final StockMovementeRepository movementRepository;
	
	public ProductController(ProductRepository productRepository, StockMovementeRepository movementRepository) {
		super();
		this.productRepository = productRepository;
		this.movementRepository = movementRepository;
	}
	
	@Operation(
			summary ="Listar todos los productos",
			description ="Retorna todos los productos registrados, sin filtrar por stock"		
	)
	@ApiResponse(responseCode = "200", description ="Lista de productos obtenida con exito")
	@GetMapping
	public List<Product>findAll(){
		return productRepository.findAll();
		
	}
	
	@Operation(
			summary="Buscar producto por ID",
			description = "Devuelve un product especifico si existe"
	)
	@ApiResponse(responseCode="200", description="Producto encontrado")
	@ApiResponse(responseCode="404", description="Producto no encontrado")
	@GetMapping("/{id}")
	public ResponseEntity<Product>getProductById(
			@Parameter(description="ID del producto a consultar", example="1")
			@PathVariable Long id){
		return productRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	@Operation(
			summary="Listar productos disponibles",
			description ="Devueve solo productos cuyo stock es mayor a cero"
	)
	@ApiResponse(responseCode="200", description="Productos disponibles listados con exito")
	@GetMapping("/available")
	public List<Product> getAvailableProducts(){
		return productRepository.findByStockGreaterThan(0);
	}
	
	@Operation(
			summary ="Obtener historial de movimientos de stock por producto",
			description ="Devuelve una lista de registros de moviemientos de stock(ventas, ajustes, etc) para un producto dado"
	)
	@ApiResponse(responseCode="200", description="Historial de movimientos obtenido exitosamenete")
	@ApiResponse(responseCode="404", description="Producto no encontrado o sin movimientos registrados")
	@GetMapping("/stock-movements/{productId}")
	public List<StockMovement> getMovements(@PathVariable Long productId){
		return movementRepository.findByProductId(productId);
	}
	
	@Operation(
			summary ="Crear un nuevo producto",
			description = "Agrega un nuevo producto al sistema con nombre, precio y stock"
	)
	@ApiResponse(responseCode ="200", description="Producto creado exitosamente")
	@PostMapping
	public Product save(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	@PutMapping("/{id}")
	@Operation(summary ="Actualizar un producto existente")
	@ApiResponse(responseCode="200", description = "Producto actualizado exitosamente")
	@ApiResponse(responseCode="404", description = "Producto no encontrado")
	public ResponseEntity<Product>updateProduct(@PathVariable Long id, @RequestBody Product updateProduct){
		
		return productRepository.findById(id)
				.map(product -> {
					product.setName(updateProduct.getName());
					product.setPrice(updateProduct.getPrice());
					product.setStock(updateProduct.getStock());
					productRepository.save(product);
					return ResponseEntity.ok(product);
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	@Operation(
			summary = "Eliminar un producto",
			description ="Elimina un producto del sistema por su ID"
	)
	@ApiResponse(responseCode="204", description ="Producto eliminado exitosamente")
	@ApiResponse(responseCode="404", description = "Producto no encontrado")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void>deleteProduct(@PathVariable Long id){
	    return productRepository.findById(id)
	            .map(product -> {
	                productRepository.delete(product);
	                return ResponseEntity.noContent().<Void>build();
	            })
	            .orElseGet(() -> ResponseEntity.notFound().build());
	}
		
	@Operation(
			summary="Disminuir stock de productos",
			description="Reduce el stock de varios productos despues de una venta"
	)
	@ApiResponse(responseCode="200", description="Stock actualizado correctamente")
	@PostMapping("/decrease-stock")
	public ResponseEntity<Void>decreaseStock(@RequestBody StockUpdateRequest request){
		
		for(StockRequest item : request.getItems()) {
			productRepository.findById(item.getProductId()).ifPresent(product ->{
				if(product.getStock() >= item.getQuantity()) {
					product.setStock(product.getStock() - item.getQuantity());
					productRepository.save(product);
					movementRepository.save(new StockMovement(product.getId(), item.getQuantity(),"VENTA"));
				}else {
					throw new RuntimeException("Stock insuficiente para producto ID"+item.getProductId());
				}
				
				
			});
		}
		
		return ResponseEntity.ok().build();
	}
}
