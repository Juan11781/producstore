package com.sales.productservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="product")
@Schema(description = "Entidad que representa un producto disponible en el sistema")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Identificador unico del producto", example ="1")
	private Long id;
	@Schema(description = "Nombre del producto", example ="Laptop Dell Inspiron 15")
	private String name;
	@Schema(description = "Precio actual del producto ", example="799.99")
	private String description;
	private Double price;
	@Schema(description = "Cantidad disponible en stock ", example="10")
	private int stock;
	
	public Product() {
		super();
	}
	
	public Product(Long id, String name, String description, Double price, int stock) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
