package com.example.empiretechtestbackendjava.repository;

import com.example.empiretechtestbackendjava.domain.entities.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageProductRepository extends JpaRepository<ImageProduct, UUID> {
}
