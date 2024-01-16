package com.example.empiretechtestbackendjava.controller;

import com.example.empiretechtestbackendjava.config.DataSourceTenantConfig;
import com.example.empiretechtestbackendjava.config.DataSourcesPropertiesConfig;
import com.example.empiretechtestbackendjava.config.JwtPropertiesConfig;
import com.example.empiretechtestbackendjava.domain.Product;
import com.example.empiretechtestbackendjava.dto.ProductRequest;
import com.example.empiretechtestbackendjava.dto.ProductResponse;
import com.example.empiretechtestbackendjava.repository.UserRepository;
import com.example.empiretechtestbackendjava.security.AuthenticationJwtFilter;
import com.example.empiretechtestbackendjava.security.SecurityConfig;
import com.example.empiretechtestbackendjava.service.JwtService;
import com.example.empiretechtestbackendjava.service.ProductService;
import com.example.empiretechtestbackendjava.service.SseService;
import com.example.empiretechtestbackendjava.service.UserDetailsServiceImpl;
import com.example.empiretechtestbackendjava.util.ProductFactoryTest;
import com.example.empiretechtestbackendjava.util.UserFactoryTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = {SecurityConfig.class, AuthenticationJwtFilter.class, UserDetailsServiceImpl.class, ProductController.class, UserRepository.class, SseService.class})
@EnableConfigurationProperties(value = {DataSourcesPropertiesConfig.class, JwtPropertiesConfig.class})
public class ProductControllerTest {

    private static final String REQUEST_URL = "/products";
    private static final String HEADER_TOKEN_NAME = "Authorization";
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper mappper;
    @MockBean
    private JwtService jwtService;
    @Mock
    private JwtPropertiesConfig jwtPropertiesConfig;
    @MockBean
    private DataSourceTenantConfig dataSourceTenantConfig;

    @BeforeEach
    public void setUp() {}

    @Test
    public void shoudCreateProduct() throws Exception {
        var productRequest = ProductFactoryTest.getRequest();
        var productSaved = ProductFactoryTest.getResponse();
        String json = mappper.writeValueAsString(productRequest);
        var token = getTokenForTests();
        MockMultipartFile partProduct = new MockMultipartFile("product", "",
                "application/json", json.getBytes());

        mockValidTokenRequest();
        BDDMockito.given(productService.createProduct(productRequest, null)).willReturn(productSaved);

        mvc.perform(MockMvcRequestBuilders.multipart(REQUEST_URL)
                    .file(partProduct)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HEADER_TOKEN_NAME, token))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andExpect(jsonPath("title").isNotEmpty());
    }

    @Test
    public void shoudGetOneProductById() throws Exception {
        var product = ProductFactoryTest.getModel();
        Long id = 1L;
        var token = getTokenForTests();

        mockValidTokenRequest();
        BDDMockito.given(productService.getProductById(id)).willReturn(product);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(REQUEST_URL + "/{id}", id)
                .header(HEADER_TOKEN_NAME, token);
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("title").isNotEmpty());
    }



    @Test
    public void shoudGetAllProductsWithoutSearch() throws Exception {
        List<ProductResponse> products = Arrays.asList(ProductFactoryTest.getResponse());
        var json = mappper.writeValueAsString(products);
        var token = getTokenForTests();

        mockValidTokenRequest();
        BDDMockito.given(productService.getAllProducts(null)).willReturn(products);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(REQUEST_URL)
                .header(HEADER_TOKEN_NAME, token);
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shoudGetProducstByTitle() throws Exception {
        var productSearch = ProductResponse.fromModel(new Product(1L, "Xbox 360", "Xbox 360 the console of Z generation",
                BigDecimal.valueOf(1000), null));
        List<ProductResponse> products = Arrays.asList(productSearch);
        String titleForSearch = "xbox";
        var token = getTokenForTests();

        mockValidTokenRequest();
        BDDMockito.given(productService.getAllProducts(titleForSearch)).willReturn(products);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(REQUEST_URL)
                .param("title", titleForSearch)
                .header(HEADER_TOKEN_NAME, token);
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void shoudRemoveProductById() throws Exception {
        Long id = 1L;
        var product = ProductFactoryTest.getModel();
        var token = getTokenForTests();

        mockValidTokenRequest();
        BDDMockito.given(productService.getProductById(id)).willReturn(product);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(REQUEST_URL + "/{id}", id)
                .header(HEADER_TOKEN_NAME, token);
        mvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void shoudUpdateProductById() throws Exception {
        Long id = 1L;
        var productForUpdate = ProductFactoryTest.getModel();
        var productRequest = new ProductRequest( "Xbox 360", "Xbox 360 the console of Z generation", BigDecimal.valueOf(1000));
        var product = ProductResponse.fromModel(new Product(1L, "Xbox 360", "Xbox 360 the console of Z generation", BigDecimal.valueOf(1000), null));
        String json = mappper.writeValueAsString(productRequest);
        var token = getTokenForTests();

        mockValidTokenRequest();
        BDDMockito.given(productService.getProductById(id)).willReturn(productForUpdate);
        BDDMockito.given(productService.updateProduct(id, productRequest)).willReturn(product);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(REQUEST_URL + "/{id}", id)
                .content(json).contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HEADER_TOKEN_NAME, token);
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("title").value(product.title()));
    }

    private void mockValidTokenRequest() {
        var userLogged = UserFactoryTest.getModel();

        BDDMockito.given(jwtService.getHeaderToken()).willReturn(HEADER_TOKEN_NAME);
        BDDMockito.given(jwtService.validAuthenticationToken(Mockito.anyString()))
                .willReturn(
                        new UsernamePasswordAuthenticationToken(
                                userLogged.getUsername(),
                                userLogged.getPassword(),
                                new ArrayList<>()));
    }

    private String getTokenForTests() {
        return "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VybmFtZSIsInRlbmFudHkiOiJ0ZW5hbnQyIiwiaWF0IjoxNzA1MzgzMTMxLCJleHAiOjE3MDUzODY3MzF9.ASD1UAhR9QscQDF1gZQFYtauLtzoQfLyQf6OGFJv6lo";
    }
}
