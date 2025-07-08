package com.sales.order_service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sales.order_service.client.ProductClient;
import com.sales.order_service.controller.OrderController;
import com.sales.order_service.dto.ProductDto;
import com.sales.order_service.entity.Order;
import com.sales.order_service.repository.OrderRepository;

public class OrderControllerTest {
	
	@Mock
	private ProductClient productClient;
	
	@Mock
	private OrderRepository orderRepository;
	
	@InjectMocks
	private OrderController orderController;
	
	private final String token = "";
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void shouldExtractEmailFromValidToken() {
		String token = "";
		
		OrderController controler = new OrderController(null, null,null);
		String email = controler.extractUserEmailFromToken(token);
		assertEquals("correo",email);
	}
	
	@Test
	void shouldCreateOrderSuccesfully() {
		Long productId = 1L;
		Order order = new Order();
		order.setProductIds(List.of(productId));
		
		ProductDto mockProduct = new ProductDto();
		mockProduct.setId(productId);
		mockProduct.setName("Laptop");
		mockProduct.setPrice(1200.00);
		mockProduct.setStock(5);
		
		when(productClient.hasSufficientStock(eq(productId),anyInt(), anyString())).thenReturn(true);
		when(productClient.getProductById(eq(productId),anyString())).thenReturn(mockProduct);
		when(orderRepository.save(any(Order.class))).thenAnswer(invocation->invocation.getArgument(0));
		
		
		Order result = orderController.createOrder(order, token);
		
		assertThat(result).isNotNull();
		assertThat(result.getUserEmail()).isEqualTo("correo");
		assertThat(result.getProductIds()).contains(productId);
		assertThat(result.getOrderedProducts()).hasSize(1);
		assertThat(result.getTotal()).isEqualTo(1200.00);
		
		verify(productClient).decreaseStock(eq(List.of(productId)),anyString());

		
	}
}
