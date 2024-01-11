package com.example.empiretechtestbackendjava.controller;

import com.example.empiretechtestbackendjava.domain.Product;
import com.example.empiretechtestbackendjava.dto.ProductRequest;
import com.example.empiretechtestbackendjava.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestPart @Valid ProductRequest product,
                                                 @RequestPart(required = false) List<MultipartFile> images) {
        var productSaved = productService.createProduct(product, images);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productSaved.getId()).toUri();

        return ResponseEntity.created(location).body(productSaved);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(value = "title", required = false) String titleSearch) {
        return ResponseEntity.ok(productService.getAllProducts(titleSearch));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long idProduto) {
        return ResponseEntity.ok(productService.getProductById(idProduto));
    }
}
