package com.sales.stockhistory.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import com.sales.stockhistory.dto.StockEvent;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	@Bean
	public ConsumerFactory<String, StockEvent> consumerFactory(){
		JsonDeserializer<StockEvent> deserializer = new JsonDeserializer<>(StockEvent.class);
		deserializer.setRemoveTypeHeaders(false);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);
		
		Map<String, Object>props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, props);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "stock-history-group");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
		return new DefaultKafkaConsumerFactory<String, StockEvent>(props, new StringDeserializer(),deserializer);
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String,StockEvent> kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, StockEvent> factory = new ConcurrentKafkaListenerContainerFactory<String, StockEvent>();
		factory.setConsumerFactory(consumerFactory());
		factory.setCommonErrorHandler(commonErrorHandler());
		return factory;
	}
	
	@Bean
	public CommonErrorHandler commonErrorHandler() {
		return new DefaultErrorHandler((recor,ex) ->{
			
			System.err.println("Error procesando evento kafka "+recor.value());
			ex.printStackTrace();
		}, new FixedBackOff(1000L,3));
	}
}
