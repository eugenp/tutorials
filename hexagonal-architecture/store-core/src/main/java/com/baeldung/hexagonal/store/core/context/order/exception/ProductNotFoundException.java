package com.baeldung.hexagonal.store.core.context.order.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("Product not found!");
    }
}
