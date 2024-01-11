package com.example.empiretechtestbackendjava.dto;

import com.example.empiretechtestbackendjava.domain.Product;
import java.math.BigDecimal;

public record ProductRequest(String title, String description, BigDecimal price) {

    public Product toModel() {
       return new Product(title, description, price);
    }
}
