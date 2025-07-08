package com.sales.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sales.auth.entity.User;
import com.sales.auth.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}




	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with: "+email));
		
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getEmail())
				.password(user.getPassword())
				.roles(user.getRole().name())
				.build();

	}

}
