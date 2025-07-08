package com.sales.order_service.dto;

public class NotificationMessage {
	private String to;
	private String subject;
	private String body;
	private String fechaHoraCompra;
	
	public NotificationMessage() {
		super();
	}
	public NotificationMessage(String to, String subject, String body, String fechaHoraCompra) {
		super();
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.fechaHoraCompra = fechaHoraCompra;
	}

	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getFechaHoraCompra() {
		return fechaHoraCompra;
	}
	public void setFechaHoraCompra(String fechaHoraCompra) {
		this.fechaHoraCompra = fechaHoraCompra;
	}
	
	
	
}
