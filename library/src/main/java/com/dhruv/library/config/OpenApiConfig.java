package com.dhruv.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Production-Ready Library API")
                        .version("1.0.0")
                        .description("In-Memory library system with RBAC, async processing, analytics, and swagger UI"));
    }
}
