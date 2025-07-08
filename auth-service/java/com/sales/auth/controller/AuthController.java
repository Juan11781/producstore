package com.sales.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.auth.dto.AuthResponse;
import com.sales.auth.dto.LoginRequest;
import com.sales.auth.dto.RegisterRequest;
import com.sales.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;
	
	
	
	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponse>register(@RequestBody RegisterRequest request){
		return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse>login(@RequestBody LoginRequest request){
		return ResponseEntity.ok(authService.authenticate(request));
	}
}
