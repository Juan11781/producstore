package com.sales.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informacion del producto usada dentro de una orden")
public class ProductDto {
	@Schema(description="ID del producto", example ="1")
	private Long id;
	@Schema(description="Nombre del producto", example="Latop Dell XPS 15")
	private String name;
	@Schema(description="Precio del producto", example="1299.99")
	private Double price;
	@Schema(description="Cantidad disponible en stokc", example="5")
	private int stock;
	
	
	
	public ProductDto() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
}
