package com.sales.auth.service;



import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sales.auth.dto.AuthResponse;
import com.sales.auth.dto.LoginRequest;
import com.sales.auth.dto.RegisterRequest;
import com.sales.auth.entity.Role;
import com.sales.auth.entity.User;
import com.sales.auth.repository.UserRepository;
import com.sales.auth.security.JwtService;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	
	
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}




	public AuthResponse register(RegisterRequest request) {
			var user = new User();
			user.setFirstname(request.getFirstname());
			user.setLastname(request.getLastname());
			user.setEmail(request.getEmail());
			user.setPassword(passwordEncoder.encode(request.getPassword()));
			user.setRole(Role.USER);
		
		userRepository.save(user);
		var jwt = jwtService.generateToken(Map.of("role",user.getRole().name()),user.getEmail());
		
		
		return new AuthResponse(jwt);
	}
	
	
	public AuthResponse authenticate(LoginRequest request) {
		authenticationManager.authenticate(
				new  UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
		);
		
		
		var user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(()-> new RuntimeException("User not found"));
		
		var jwt = jwtService.generateToken(Map.of("role",user.getRole().name()), user.getEmail());
		
		return new AuthResponse(jwt);
		
	}
}
