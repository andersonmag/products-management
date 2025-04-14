package com.example.empiretechtestbackendjava.repository;

import com.example.empiretechtestbackendjava.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByTitleContaining(String titleSearch);
}
