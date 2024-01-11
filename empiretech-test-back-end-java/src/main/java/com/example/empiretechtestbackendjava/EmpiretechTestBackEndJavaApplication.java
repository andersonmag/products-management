package com.example.empiretechtestbackendjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class EmpiretechTestBackEndJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpiretechTestBackEndJavaApplication.class, args);
    }

    @GetMapping
    public String teste() {
        return "Hello World";
    }
}
