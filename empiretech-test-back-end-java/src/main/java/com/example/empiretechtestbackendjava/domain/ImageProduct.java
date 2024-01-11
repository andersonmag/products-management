package com.example.empiretechtestbackendjava.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class ImageProduct {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private Product product;
    private String link;
}
