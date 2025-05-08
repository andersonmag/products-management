package com.example.empiretechtestbackendjava.controller;

import com.example.empiretechtestbackendjava.domain.dtos.ProductRequest;
import com.example.empiretechtestbackendjava.domain.dtos.ProductResponse;
import com.example.empiretechtestbackendjava.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/products")
@Tag(name = "Products", description = "Endpoints for products CRUD")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Create a product", description = "Create a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully created",
                    headers = @Header(name = "Location", description = "URI of the created product", required = true)),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Not authenticated"),
    })
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Parameter(description = "Product data to create", required = true)
            @RequestPart("product") @Valid ProductRequest product,
            @Parameter(description = "List of images for the product")
            @RequestPart(required = false) List<MultipartFile> images) {
        var productSaved = productService.createProduct(product, images);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productSaved.id()).toUri();
        return ResponseEntity.created(location).body(productSaved);
    }

    @Operation(summary = "Get products", description = "Returns a list of all products, optionally filtered by title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @Parameter(description = "Optional filter to search products by title")
            @RequestParam(value = "title", required = false) String titleSearch
    ) {
        return ResponseEntity.ok(productService.getAllProducts(titleSearch));
    }

    @Operation(summary = "Get product by ID", description = "Returns a single product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get Product"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(
            @Parameter(description = "ID of the product to retrieve", required = true)
            @PathVariable("id") Long idProduto
    ) {
        return ResponseEntity.ok(ProductResponse.fromModel(productService.getProductById(idProduto)));
    }

    @Operation(summary = "Remove product by ID", description = "Deletes a single product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(
            @Parameter(description = "ID of the product to delete", required = true)
            @PathVariable("id") Long idProduto
    ) {
        productService.removeProductById(idProduto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update product by ID", description = "Updates a product with new data based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully updated"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "ID of the product to update", required = true)
            @PathVariable("id") Long idProduto,
            @Parameter(description = "New product data", required = true)
            @RequestBody @Valid ProductRequest product) {
        return ResponseEntity.ok(productService.updateProduct(idProduto, product));
    }

    @Operation(summary = "Remove product image by ID", description = "Removes a product image based on its ID and product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Image successfully removed"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Image not found or does not belong to the specified product"),
    })
    @DeleteMapping("/{id}/images/{imageId}")
    public ResponseEntity<Void> removeImageProduct(
            @Parameter(description = "ID of the product", required = true)
            @PathVariable("id") Long productId,
            @Parameter(description = "ID of the product image to remove", required = true)
            @PathVariable("imageId") UUID imageId) {
        productService.removeProductImage(imageId, productId);
        return ResponseEntity.noContent().build();
    }
}
