package com.baeldung.hexagonal.core;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long productId) {
        super("product not found by id " + productId);
    }
}
