package com.example.empiretechtestbackendjava.controller;

import com.example.empiretechtestbackendjava.domain.Product;
import com.example.empiretechtestbackendjava.service.ProductService;
import com.example.empiretechtestbackendjava.util.ProductFactoryTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    private static final String REQUEST_URL = "/products";
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductService productService;
    @Autowired
    private ObjectMapper mappper;

    @Test
    public void shoudCreateProduct() throws Exception {
        var productRequest = ProductFactoryTest.getRequest();
        var productSaved = ProductFactoryTest.getModel();

        String json = mappper.writeValueAsString(productRequest);
        MockMultipartFile partProduct = new MockMultipartFile("product", "",
                "application/json", json.getBytes());

        BDDMockito.given(productService.createProduct(productRequest, null)).willReturn(productSaved);

        mvc.perform(MockMvcRequestBuilders.multipart(REQUEST_URL)
                    .file(partProduct)
                    .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andExpect(jsonPath("title").isNotEmpty());
    }

    @Test
    public void shoudGetOneProductById() throws Exception {
        var product = ProductFactoryTest.getModel();
        Long id = 1L;

        BDDMockito.given(productService.getProductById(id)).willReturn(product);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(REQUEST_URL + "/{id}", id);
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("title").isNotEmpty());
    }

    @Test
    public void shoudGetAllProductsWithoutSearch() throws Exception {
        List<Product> products = Arrays.asList(ProductFactoryTest.getModel());
        String json = mappper.writeValueAsString(products);

        BDDMockito.given(productService.getAllProducts(null)).willReturn(products);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(REQUEST_URL);
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shoudGetProducstByTitle() throws Exception {
        var productSearch = new Product(1L, "Xbox 360", "Xbox 360 the console of Z generation", BigDecimal.valueOf(1000), null);
        List<Product> products = Arrays.asList(productSearch);
        String json = mappper.writeValueAsString(products);

        String titleForSearch = "xbox";

        BDDMockito.given(productService.getAllProducts(titleForSearch)).willReturn(products);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(REQUEST_URL)
                .param("title", titleForSearch);
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void shoudRemoveProductById() throws Exception {
        Long id = 1L;
        var product = ProductFactoryTest.getModel();

        BDDMockito.given(productService.getProductById(id)).willReturn(product);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(REQUEST_URL + "/{id}", id);
        mvc.perform(request).andExpect(status().isOk());
    }

}
