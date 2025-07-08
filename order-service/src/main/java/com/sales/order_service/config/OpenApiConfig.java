package com.sales.order_service.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
				title="Order Service API",
				version="1.0",
				description="Gestion de ordenes de compra de productos con stock validado"
		)
)

@Configuration
public class OpenApiConfig {

}
