package com.example.empiretechtestbackendjava.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Products Management System")
                        .version("1.0")
                        .description("API Products Management System")
                        .contact(new Contact()
                                .name("Anderson Delmondes")
                                .email("andersondel.dev@gmail.com")
                                .url("https://github.com/andersonmag")));
    }
}
