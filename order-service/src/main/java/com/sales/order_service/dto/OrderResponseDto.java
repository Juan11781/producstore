package com.sales.order_service.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description="Respuesta de una orden con productos y total")
public class OrderResponseDto {
	
	@Schema(description = "ID de la orden",example="1001")
	private Long id;
	@Schema(description = "Correo del usuario que genero la orden", example="juan@example.com")
	private String userEmail;
	@Schema(description ="Fecha y hora de creacion de la orden", example="2025")
	private LocalDateTime createdAt;
	@Schema(description =  "Total de la orden", example="259.99")
	private Double total;
	@Schema(description="Lista de productos asociados a la orden")
	private List<ProductDto> products;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public List<ProductDto> getProducts() {
		return products;
	}
	public void setProducts(List<ProductDto> products) {
		this.products = products;
	}
	
	
	
	
}
