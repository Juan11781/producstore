package com.sales.productservice.dto;

import java.util.List;

public class StockUpdateRequest {
	private List<StockRequest> items;

	public StockUpdateRequest() {
		super();
	}

	public StockUpdateRequest(List<StockRequest> items) {
		super();
		this.items = items;
	}

	public List<StockRequest> getItems() {
		return items;
	}

	public void setItems(List<StockRequest> items) {
		this.items = items;
	}

}
