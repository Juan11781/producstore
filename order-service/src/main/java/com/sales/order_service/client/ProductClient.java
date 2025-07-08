package com.sales.order_service.client;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.sales.order_service.dto.ProductDto;
import com.sales.order_service.dto.StockRequest;
import com.sales.order_service.dto.StockUpdateRequest;

import reactor.core.publisher.Mono;

@Component
public class ProductClient {
	private final WebClient webClient;

	public ProductClient(WebClient.Builder webClientBuilder) {
		super();
		this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
	}
	
	public void decreaseStock(List<Long>productsIds, String token) {
		
		List<StockRequest>requests = productsIds.stream()
				.map(id -> new StockRequest(id,1))
				.toList();
		
		webClient.post()
		    .uri("/products/decrease-stock")
		    .bodyValue(new StockUpdateRequest(requests))
		    .header("Authorization", "Bearer "+token)
		    .retrieve()
		    .bodyToMono(Void.class)
		    .doOnError(e -> System.out.println("Error al disminuir stock: "+e.getMessage()))
		    .block();
	}
	
	public ProductDto getProductById(Long productId,String token) {
		return webClient.get()
				.uri("/products/{id}", productId)
				.header("Authorization","Bearer "+token)
				.retrieve()
				.onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
							response ->{
								System.out.println("Error al llamar al product service: "+response.statusCode());
								return Mono.error(new RuntimeException("Error al llamar a product service"));
							})
				.bodyToMono(ProductDto.class)
				.onErrorResume(e->{
					System.out.println("Error: "+e.getMessage());
					return Mono.empty();
				})
				.block();
	}
	
	
	public boolean hasSufficientStock(Long productId, int quantity, String token) {
		ProductDto product =  getProductById(productId, token);
		return product != null && product.getStock() >= quantity;
		
	}
	
}
