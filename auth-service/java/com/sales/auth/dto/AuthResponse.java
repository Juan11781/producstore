package com.sales.auth.dto;

public class AuthResponse {
	private String token;

	public AuthResponse(String token) {
		super();
		this.token = token;
	}

	public AuthResponse() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
