package com.example.notification.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
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

import com.example.notification.dto.NotificationMessage;


@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	@Bean
	public ConsumerFactory<String, NotificationMessage> consumerFactory(){
		JsonDeserializer<NotificationMessage>deserializer = new JsonDeserializer<>(NotificationMessage.class);
		deserializer.setRemoveTypeHeaders(false);
		deserializer.addTrustedPackages("com.example.notification.dto");
		deserializer.setUseTypeMapperForKey(true);
		
		Map<String, Object>props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);

		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
	}
	
	@Bean(name="kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, NotificationMessage> kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, NotificationMessage>
		factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setCommonErrorHandler(commonErrorHandler());
		return factory;
	}
	
	@Bean
	public CommonErrorHandler commonErrorHandler() {
		FixedBackOff fixedBackOff = new FixedBackOff(1000L,3)
				;
		DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler(
				(consumerRecord, exception)->{
					System.err.println("Error consumiendo mensaje: "+consumerRecord.value());
					exception.printStackTrace();
				},
				fixedBackOff
		);
		
		return defaultErrorHandler;
	}
}
