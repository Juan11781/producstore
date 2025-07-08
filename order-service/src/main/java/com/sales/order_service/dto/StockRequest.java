package com.sales.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description ="Representa un cambio de stock para un producto")
public class StockRequest {
	@Schema(description ="ID del producto", example="1")
	private Long productId;
	@Schema(description ="Cantidad a disminuir", example ="1")
	private int quantity;
	
	
	
	public StockRequest() {
		super();
	}



	public StockRequest(Long productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
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
	
	
	
	
}
