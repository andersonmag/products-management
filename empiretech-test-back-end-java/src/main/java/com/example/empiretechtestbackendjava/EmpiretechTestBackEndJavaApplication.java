package com.example.empiretechtestbackendjava;

import com.example.empiretechtestbackendjava.config.JwtPropertiesConfig;
import com.example.empiretechtestbackendjava.config.S3Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableConfigurationProperties({JwtPropertiesConfig.class, S3Config.class})
@EnableCaching
public class EmpiretechTestBackEndJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpiretechTestBackEndJavaApplication.class, args);
    }
}
