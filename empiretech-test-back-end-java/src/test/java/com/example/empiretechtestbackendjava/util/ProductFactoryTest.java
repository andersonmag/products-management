package com.example.empiretechtestbackendjava.util;

import com.example.empiretechtestbackendjava.domain.Product;
import com.example.empiretechtestbackendjava.dto.ProductRequest;
import com.example.empiretechtestbackendjava.dto.ProductResponse;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ProductFactoryTest {

    public static ProductRequest getRequest() {
        return new ProductRequest("PS5", "The new Playstation´s console", BigDecimal.valueOf(3000));
    }

    public static Product getModel() {
        return new Product(1L, "PS5", "The new Playstation´s console", BigDecimal.valueOf(3000), new ArrayList<>());
    }

    public static ProductResponse getResponse() {
       return ProductResponse.fromModel(getModel());
    }
}
