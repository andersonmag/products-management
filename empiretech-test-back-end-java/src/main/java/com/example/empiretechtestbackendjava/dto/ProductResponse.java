package com.example.empiretechtestbackendjava.dto;

import com.example.empiretechtestbackendjava.domain.ImageProduct;
import com.example.empiretechtestbackendjava.domain.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(Long id, String title, String description, BigDecimal price, List<ImageProduct> images) implements Serializable {
    public static ProductResponse fromModel(Product productSaved) {
        return new ProductResponse(productSaved.getId(), productSaved.getTitle(),
                productSaved.getDescription(), productSaved.getPrice(),
                productSaved.getImages());
    }
}
