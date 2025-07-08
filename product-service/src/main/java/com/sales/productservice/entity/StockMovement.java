package com.sales.productservice.entity;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Schema(description ="Historial de cambios de inventarios por producto")
public class StockMovement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "ID del producto de stock", example ="1001")
	private Long id;
	@Schema(description = "ID del producto afectado", example ="1")
	private Long productId;
	@Schema(description ="Cantidad afectada en el movimiento", example="2")
	private int quantity;
	@Schema(description ="Tipo de movimiento", example ="VENTA")
	private String type;
	@Schema(description = "Fecha y hora del movimiento",example="2025-06-04T10:30:00")
	private LocalDateTime createdAt;
	
	public StockMovement() {
		super();
	}
	
	
	
	
	public StockMovement(Long productId, int quantity, String type) {
		super();
		this.productId = productId;
		this.quantity = quantity;
		this.type = type;
		this.createdAt = LocalDateTime.now();
	}




	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	

}
