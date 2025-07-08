package com.sales.order_service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.sales.order_service.dto.OrderResponseDto;
import com.sales.order_service.dto.ProductDto;
import com.sales.order_service.entity.Order;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	private final String token = "";
	
	@Test
	void shouldCreateOrderSuccesfully() {
		
		 Order orderRequest =new Order();
		 orderRequest.setProductIds(List.of(1L, 1L, 1L));

		 
		 HttpHeaders headers = new HttpHeaders();
		 headers.setContentType(MediaType.APPLICATION_JSON);
		 headers.setBearerAuth(token.substring(7));
		 
		 HttpEntity<Order>entity = new HttpEntity<>(orderRequest, headers);
		 
		 ResponseEntity<Order> response =  restTemplate.postForEntity("/orders", entity, Order.class);
		 assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		 assertThat(response.getBody()).isNotNull();
		 assertThat(response.getBody().getProductIds()).contains(1L);
	
	
	
	}

}
