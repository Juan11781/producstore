package com.sales.order_service.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Lisrta de cambios de stock que se aplicara a los productos")
public class StockUpdateRequest {
	
	@Schema(description = "Lista de moviemientos de stock")
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
