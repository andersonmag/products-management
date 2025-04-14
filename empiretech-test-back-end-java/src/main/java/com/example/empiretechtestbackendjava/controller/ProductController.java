package com.example.empiretechtestbackendjava.controller;

import com.example.empiretechtestbackendjava.domain.dtos.ProductRequest;
import com.example.empiretechtestbackendjava.domain.dtos.ProductResponse;
import com.example.empiretechtestbackendjava.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestPart("product") @Valid ProductRequest product,
                                                         @RequestPart(required = false) List<MultipartFile> images) {
        var productSaved = productService.createProduct(product, images);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productSaved.id()).toUri();
        return ResponseEntity.created(location).body(productSaved);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(@RequestParam(value = "title", required = false) String titleSearch) {
        return ResponseEntity.ok(productService.getAllProducts(titleSearch));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long idProduto) {
        return ResponseEntity.ok(ProductResponse.fromModel(productService.getProductById(idProduto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeProduct(@PathVariable("id") Long idProduto) {
        productService.removeProductById(idProduto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long idProduto,
                                                         @RequestBody @Valid ProductRequest product) {
        var productUpdated = productService.updateProduct(idProduto, product);
        return ResponseEntity.ok(productUpdated);
    }
}
