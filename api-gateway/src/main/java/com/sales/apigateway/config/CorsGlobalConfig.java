package com.sales.apigateway.config;

import java.util.List;
/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
*/

//@Configuration

public class CorsGlobalConfig {
	/*
	@Bean
	public CorsWebFilter corsWebFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOriginPatterns(List.of("http://localhost:4200"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowedMethods(List.of("GET","POST","DELETE","OPTIONS"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return new CorsWebFilter(source);
	}*/
}
