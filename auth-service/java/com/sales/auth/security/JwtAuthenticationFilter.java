package com.sales.auth.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sales.auth.service.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtService jwtService;
	private final UserDetailsServiceImpl userDetailsService;
	
	

	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
		super();
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		jwt = authHeader.substring(7);
		
		try {
			userEmail = jwtService.extractUsername(jwt);
		}catch(ExpiredJwtException e) {
			filterChain.doFilter(request, response);
			return;
		}
		
		if(userEmail !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
			
			if(jwtService.isTokenValid(jwt, userDetails.getUsername())) {
				UsernamePasswordAuthenticationToken authToken 
				= new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
			
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
