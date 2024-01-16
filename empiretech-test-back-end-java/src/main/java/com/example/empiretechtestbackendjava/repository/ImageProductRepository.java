package com.example.empiretechtestbackendjava.repository;

import com.example.empiretechtestbackendjava.domain.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProduct, UUID> {
}
