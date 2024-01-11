package com.example.empiretechtestbackendjava.repository;

import com.example.empiretechtestbackendjava.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByTitleContaining(String titleSearch);
}
