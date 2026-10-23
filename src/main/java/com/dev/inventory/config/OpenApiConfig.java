package com.dev.inventory.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Inventory Management API")
                        .version("1.0.0")
                        .description("High-performance RESTful API for Product Catalog and Stock Control built with Spring Boot 3 & PostgreSQL.")
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")));
    }
}
