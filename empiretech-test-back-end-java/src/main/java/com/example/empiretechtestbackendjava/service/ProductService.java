package com.example.empiretechtestbackendjava.service;

import com.example.empiretechtestbackendjava.domain.ImageProduct;
import com.example.empiretechtestbackendjava.domain.Product;
import com.example.empiretechtestbackendjava.dto.ProductRequest;
import com.example.empiretechtestbackendjava.repository.ImageProductRepository;
import com.example.empiretechtestbackendjava.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ImageProductRepository imageRepository;
    private final S3Service s3Service;
    private final String PATH_SAVE_IMAGES = "products";

    @Transactional
    @CacheEvict(value = "products", keyGenerator = "customKeyCacheConfig", allEntries = true)
    public Product createProduct(ProductRequest productRequest, List<MultipartFile> images) {
        Product productSaved = productRepository.save(productRequest.toModel());

        if(images != null && !images.isEmpty()) {
            saveImages(images, productSaved);
        }

        return productSaved;
    }

    private void saveImages(List<MultipartFile> images, Product productSaved) {
        for (MultipartFile file : images) {
            var imageName = file.getOriginalFilename();
            var completePath = PATH_SAVE_IMAGES + "/" + imageName;

            var pathSaved = "";
            try {
                pathSaved = s3Service.uploadFile(completePath, file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ImageProduct image = new ImageProduct(productSaved, imageName, pathSaved, file.getContentType());
            imageRepository.save(image);
            productSaved.getImages().add(image);
        }
    }

    @Cacheable(value = "products", keyGenerator = "customKeyCacheConfig", condition = "#titleSearch == null")
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

    @CacheEvict(value = "products", keyGenerator = "customKeyCacheConfig", allEntries = true)
    public void removeProductById(Long idProduto) {
        Product product = getProductById(idProduto);
        productRepository.delete(product);
    }

    @Transactional
    @CacheEvict(value = "products", keyGenerator = "customKeyCacheConfig", allEntries = true)
    public Product updateProduct(Long idProduto, ProductRequest product) {
        Product productForUpdate = getProductById(idProduto);
        productForUpdate = new Product(productForUpdate.getId(), product.title(), product.description(), product.price(), null);

        return productRepository.save(productForUpdate);
    }
}
