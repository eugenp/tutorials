package com.baeldung.jacocoexclusions.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductServiceUnitTest {

    @Test
    public void givenOriginalPrice_whenGetSalePrice_thenReturnsDiscountedPrice() {
        ProductService productService = new ProductService();
        double salePrice = productService.getSalePrice(100, true);
        assertEquals(salePrice, 75);
    }

    @Test
    public void givenOriginalPrice_whenGetSalePriceWithFlagFalse_thenReturnsDiscountedPrice() {
        ProductService productService = new ProductService();
        double salePrice = productService.getSalePrice(100, false);
        assertEquals(salePrice, 100);
    }
}
