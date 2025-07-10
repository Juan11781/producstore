package com.sales.stockhistory.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.sales.stockhistory.dto.StockEvent;
import com.sales.stockhistory.service.StockHistoryService;

@Component
public class StockEventConsumer {
	
	private final StockHistoryService service;
	private static final Logger logger = LoggerFactory.getLogger(StockEventConsumer.class);
	public StockEventConsumer(StockHistoryService service) {
		super();
		this.service = service;
	}


	@KafkaListener(topics = "stock-events", groupId ="stock-history-group", containerFactory="kafkaListenerContainerFactory")
	public void consumer(StockEvent event) {
	
		logger.info("Evento recibido desde kafka: {}",event);
		logger.info("Guardando evento en base de datos.....");
		service.saveStockEvent(event);
	}
}
