package com.example.empiretechtestbackendjava.service;

import com.example.empiretechtestbackendjava.repository.ProductRepository;
import com.example.empiretechtestbackendjava.util.ProductFactoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @Test
    public void shouldCreateProduct() {
        var productRequest = ProductFactoryTest.getRequest();

        productService.createProduct(productRequest, null);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    public void shouldGetProducts() {
        productService.getAllProducts(null);

        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void shouldGetProductsByTitleSearch() {
        String titleSearch = "xbox";

        productService.getAllProducts(titleSearch);

        verify(productRepository, times(1)).findAllByTitleContaining(titleSearch);
    }

    @Test
    public void shouldGetProductIdById() {
        var product = ProductFactoryTest.getModel();
        Long id = 1L;

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Assertions.assertEquals(id, productService.getProductById(id).getId());
    }

    @Test
    public void shouldThowExceptionWhenGetProductWithIdInexistent() {
        Long id = 1L;

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(id));
    }
}
