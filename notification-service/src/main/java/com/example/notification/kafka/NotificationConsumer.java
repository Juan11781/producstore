package com.example.notification.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.notification.dto.NotificationMessage;
import com.example.notification.service.EmailService;

@Component
public class NotificationConsumer {
	private final EmailService emailService;

	public NotificationConsumer(EmailService emailService) {
		super();
		this.emailService = emailService;
	};
	
	
	@KafkaListener(topics ="email-topic", groupId ="notification-group", containerFactory="kafkaListenerContainerFactory")
	public void consume(NotificationMessage message) {
			try {
				System.out.println("Mensaje recibido de kafka: "+message.getTo());
				System.out.println("Fecha de compra"+message.getFechaHoraCompra());
				emailService.sendEmail(message.getTo(),message.getSubject(),message.getBody());
				System.out.println("Correo enviado atravez de kafka: "+message.getTo());
			}catch(Exception ex) {
				System.err.println("Error al enviar el correo desde kaftka: "+ex.getMessage());
			}
	}
	
}
