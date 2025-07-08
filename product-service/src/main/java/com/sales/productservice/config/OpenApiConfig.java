package com.sales.productservice.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
		info = @Info(
				title ="Product Service API",
				version = "1.0",
				description = "Gestion de productos con stock y precios"
		)
)
public class OpenApiConfig {

}
