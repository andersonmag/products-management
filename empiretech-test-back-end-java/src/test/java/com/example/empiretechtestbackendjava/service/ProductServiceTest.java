package com.example.empiretechtestbackendjava.service;

import com.example.empiretechtestbackendjava.domain.entities.Product;
import com.example.empiretechtestbackendjava.repository.ImageProductRepository;
import com.example.empiretechtestbackendjava.repository.ProductRepository;
import com.example.empiretechtestbackendjava.util.ProductFactoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ImageProductRepository imageProductRepository;
    private ProductService productService;

    @Mock
    private S3Service s3Service;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, imageProductRepository, s3Service);
    }

    @Test
    public void shouldCreateProductWithoutImages() {
        var productRequest = ProductFactoryTest.getRequest();

        Mockito.when(productRepository.save(any(Product.class))).thenReturn(ProductFactoryTest.getModel());
        var productSaved = productService.createProduct(productRequest, null);

        verify(productRepository, times(1)).save(any());
        verify(productRepository, times(1)).save(any());
        int quantidadeImagensRetorno = 0;
        Assertions.assertEquals(quantidadeImagensRetorno, productSaved.images().size());
    }

    @Test
    public void shouldCreateProductWithImage() {
        var productRequest = ProductFactoryTest.getRequest();
        var fileNameTest = "image-teste.jpg";
        MockMultipartFile image = new MockMultipartFile(fileNameTest, fileNameTest,
                "image/jpeg", fileNameTest.getBytes());

        Mockito.when(productRepository.save(any(Product.class))).thenReturn(ProductFactoryTest.getModel());
        var productSaved = productService.createProduct(productRequest, Arrays.asList(image));

        verify(productRepository, times(1)).save(any());
        verify(imageProductRepository, times(1)).save(any());
        int quantidadeImagensRetorno = 1;
        Assertions.assertEquals(quantidadeImagensRetorno, productSaved.images().size());
        Assertions.assertEquals(image.getName(), productSaved.images().get(0).getName());
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

    @Test
    public void shouldRemoveProductById() {
        Long id = 1L;
        var product = ProductFactoryTest.getModel();

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> productService.removeProductById(id));
        verify(productRepository, times(1)).delete(product);
    }
}
