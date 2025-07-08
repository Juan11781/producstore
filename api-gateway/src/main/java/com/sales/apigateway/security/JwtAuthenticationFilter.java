package com.sales.apigateway.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.core.HttpHeaders;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {
	
	@Value("${jwt.secret}")
	private  String SECRET_KEY;
	
	
	
	private Key getSigninKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}	

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
	
		
		String path = exchange.getRequest().getPath().toString();
		HttpMethod method = exchange.getRequest().getMethod();
		
		  if (method == HttpMethod.OPTIONS) {
		        return chain.filter(exchange);
		  }
	
		if(path.startsWith("/auth/login") || path.startsWith("/auth/register")) {
			return chain.filter(exchange);
		}
		
		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
		
		String token = authHeader.substring(7);
		String username;	
		try {
			Claims claim = Jwts.parserBuilder()
			    .setSigningKey(getSigninKey())
			    .build()
			    .parseClaimsJws(token).getBody();
			username = claim.getSubject();
			
	
			 UsernamePasswordAuthenticationToken authentication 
			 = new UsernamePasswordAuthenticationToken(username, null, List.of());
			 
			 return chain.filter(exchange).contextWrite(
					 	ReactiveSecurityContextHolder.withSecurityContext(Mono.just(new SecurityContextImpl(authentication)))
					 );
			   
		}catch(Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}

	}
	
	
	

 
}
