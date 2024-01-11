package com.example.empiretechtestbackendjava.repository;

import com.example.empiretechtestbackendjava.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
