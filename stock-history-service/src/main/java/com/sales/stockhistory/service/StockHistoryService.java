package com.sales.stockhistory.service;

import org.springframework.stereotype.Service;

import com.sales.stockhistory.dto.StockEvent;
import com.sales.stockhistory.entity.StockHistory;
import com.sales.stockhistory.repository.StockHistoryRepository;

@Service
public class StockHistoryService {
	
	private final StockHistoryRepository repository;

	public StockHistoryService(StockHistoryRepository repository) {
		super();
		this.repository = repository;
	}



	public  void saveStockEvent(StockEvent event) {
		StockHistory history = new StockHistory();
		
		history.setProductId(event.getProductId());
		history.setAction(event.getAction());
		history.setQuantityChanged(event.getQuantityChanged());
		history.setTimeStamp(event.getTimeStamp());
		history.setOrigin(event.getOrigin());
		
		repository.save(history);
	}
}
