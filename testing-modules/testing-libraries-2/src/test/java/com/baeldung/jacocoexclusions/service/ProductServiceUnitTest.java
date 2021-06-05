package com.baeldung.jacocoexclusions.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductServiceUnitTest {

    @Test
    public void givenOriginalPrice_whenGetSalePrice_thenReturnsDiscountedPrice() {
        ProductService productService = new ProductService();
        double salePrice = productService.getSalePrice(100);
        assertEquals(salePrice, 75);
    }
}
