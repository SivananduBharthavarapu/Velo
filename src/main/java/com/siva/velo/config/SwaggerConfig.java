package com.siva.velo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI veloAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("VELO Chat API")
                        .description("Backend REST API for VELO Chat Application")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Sivanandu Bharthavarapu")));
    }
}