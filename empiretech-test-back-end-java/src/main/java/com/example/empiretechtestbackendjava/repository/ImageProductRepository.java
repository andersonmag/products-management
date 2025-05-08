package com.example.empiretechtestbackendjava.repository;

import com.example.empiretechtestbackendjava.domain.entities.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ImageProductRepository extends JpaRepository<ImageProduct, UUID> {
    
    @Query("SELECT i FROM ImageProduct i WHERE i.id = :id AND i.product.id = :productId")
    Optional<ImageProduct> findByIdAndProductId(@Param("id") UUID id, @Param("productId") Long productId);
}
