package com.sales.stockhistory.dto;

import java.time.LocalDateTime;

public class StockEvent {
	private String productId;
	private String action;
	private int quantityChanged;
	private LocalDateTime timeStamp;
	private String origin;

	public StockEvent(String productId, String action, int quantityChanged, LocalDateTime timeStamp, String origin) {
		super();
		this.productId = productId;
		this.action = action;
		this.quantityChanged = quantityChanged;
		this.timeStamp = timeStamp;
		this.origin = origin;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getQuantityChanged() {
		return quantityChanged;
	}
	public void setQuantityChanged(int quantityChanged) {
		this.quantityChanged = quantityChanged;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	
}
