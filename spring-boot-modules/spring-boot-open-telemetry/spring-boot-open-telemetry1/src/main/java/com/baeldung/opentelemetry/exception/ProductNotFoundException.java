package com.baeldung.opentelemetry.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productNotFound) {
        super(productNotFound);
    }
}
