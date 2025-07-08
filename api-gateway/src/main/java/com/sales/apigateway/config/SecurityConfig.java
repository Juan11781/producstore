package com.sales.apigateway.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.sales.apigateway.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
	 private final JwtAuthenticationFilter jwtAuthenticationFilter;
	 
	 
	 public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		super();
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}


	@Bean
	    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
	        return http
	            .csrf(csrf -> csrf.disable())
	            .cors(cors -> cors.configurationSource(corsConfigurationSource()))//linea agregada para corsConfigurations
	            .authorizeExchange(exchange -> exchange
	                .pathMatchers("/auth/**").permitAll()
	                .anyExchange().authenticated()
	            )
	            .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
	            .build();
	    }
	/**agregado para solucionar cors**/
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
