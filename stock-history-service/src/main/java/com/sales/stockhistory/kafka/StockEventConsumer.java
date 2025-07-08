package com.sales.stockhistory.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.sales.stockhistory.dto.StockEvent;
import com.sales.stockhistory.service.StockHistoryService;

@Component
public class StockEventConsumer {
	
	private final StockHistoryService service;

	public StockEventConsumer(StockHistoryService service) {
		super();
		this.service = service;
	}


	@KafkaListener(topics = "stock-events", groupId ="stock-history-group", containerFactory="kafkaListenerFactory")
	public void consumer(StockEvent event) {
		System.out.println("Evento recibido de kafka: "+event);
		service.saveStockEvent(event);
	}
}
