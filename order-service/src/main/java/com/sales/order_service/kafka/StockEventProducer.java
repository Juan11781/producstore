package com.sales.order_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.sales.order_service.dto.StockEvent;

@Component
public class StockEventProducer {
	private final KafkaTemplate<String, StockEvent>kafkaTemplate;
	
	public StockEventProducer(KafkaTemplate<String,StockEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendStockEvent(StockEvent event) {
		kafkaTemplate.send("stock-events", event.getProductId(), event);
	}
}
