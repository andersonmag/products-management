package com.example.empiretechtestbackendjava.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String title;
    @Column(nullable = true)
    private String description;
    @Column(nullable = true)
    private BigDecimal price;

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    List<ImageProduct> images;

    public Product(String title, String description, BigDecimal price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

}
