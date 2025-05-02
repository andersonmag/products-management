package com.example.empiretechtestbackendjava.service;

import com.example.empiretechtestbackendjava.domain.dtos.ProductRequest;
import com.example.empiretechtestbackendjava.domain.dtos.ProductResponse;
import com.example.empiretechtestbackendjava.domain.entities.Product;
import com.example.empiretechtestbackendjava.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ImageService imageService;

    @Transactional
    @CacheEvict(value = "products", keyGenerator = "customKeyCacheConfig", allEntries = true)
    public ProductResponse createProduct(ProductRequest productRequest, List<MultipartFile> images) {
        Product productSaved = productRepository.save(productRequest.toModel());

        if (images != null && !images.isEmpty()) {
            imageService.saveImages(images, productSaved);
        }

        return ProductResponse.fromModel(productSaved);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "products", keyGenerator = "customKeyCacheConfig", condition = "#titleSearch == null")
    public List<ProductResponse> getAllProducts(String titleSearch) {
        return (titleSearch != null ?
                productRepository.findAllByTitleContaining(titleSearch)
                : productRepository.findAll())
                .stream()
                .map(ProductResponse::fromModel)
                .toList();
    }

    public Product getProductById(Long idProduto) {
        return productRepository.findById(idProduto).orElseThrow(()
                -> new IllegalArgumentException("Id de produto não encontrado"));
    }

    @Transactional
    @CacheEvict(value = "products", keyGenerator = "customKeyCacheConfig", allEntries = true)
    public void removeProductById(Long idProduto) {
        Product product = getProductById(idProduto);
        productRepository.delete(product);
    }

    @Transactional
    @CacheEvict(value = "products", keyGenerator = "customKeyCacheConfig", allEntries = true)
    public ProductResponse updateProduct(Long idProduto, ProductRequest product) {
        Product productForUpdate = getProductById(idProduto);
        productForUpdate = new Product(productForUpdate.getId(), product.title(),
                product.description(), product.price(), null);

        Product updated = productRepository.save(productForUpdate);
        return ProductResponse.fromModel(updated);
    }
}
