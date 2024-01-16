package com.example.empiretechtestbackendjava.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class ImageProduct {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JsonIgnore
    @ManyToOne
    private Product product;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String link;
    private String contentType;

    public ImageProduct(Product product, String name, String link, String contentType) {
        this.product = product;
        this.name = name;
        this.link = link;
        this.contentType = contentType;
    }
}
