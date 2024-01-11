package com.example.empiretechtestbackendjava.service;

import com.example.empiretechtestbackendjava.domain.Product;
import com.example.empiretechtestbackendjava.dto.ProductRequest;
import com.example.empiretechtestbackendjava.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    public Product createProduct(ProductRequest productRequest, List<MultipartFile> images) {
        Product productSaved = productRepository.save(productRequest.toModel());
        return productSaved;
    }

    public List<Product> getAllProducts(String titleSearch) {
        if(titleSearch != null) {
            return productRepository.findAllByTitleContaining(titleSearch);
        }
        return productRepository.findAll();
    }

    public Product getProductById(Long idProduto) {
        return productRepository.findById(idProduto).orElseThrow(()
                -> new IllegalArgumentException("Id de produto não encontrado"));
    }
}
