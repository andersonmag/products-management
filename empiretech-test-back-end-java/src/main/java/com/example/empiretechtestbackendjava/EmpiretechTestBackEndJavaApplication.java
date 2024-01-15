package com.example.empiretechtestbackendjava;

import com.example.empiretechtestbackendjava.config.JwtPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtPropertiesConfig.class)
public class EmpiretechTestBackEndJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpiretechTestBackEndJavaApplication.class, args);
    }
}
