package com.sales.productservice.dto;

public class StockRequest {

	private Long productId;
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
