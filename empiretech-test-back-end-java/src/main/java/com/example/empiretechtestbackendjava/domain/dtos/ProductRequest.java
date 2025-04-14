package com.example.empiretechtestbackendjava.domain.dtos;

import com.example.empiretechtestbackendjava.domain.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(@NotBlank(message = "Valor não pode ser vazio") String title,
                             @NotBlank(message = "Valor não pode ser vazio") String description,
                             @NotNull(message = "Valor não pode ser vazio") BigDecimal price) {

    public Product toModel() {
       return new Product(title, description, price);
    }
}
