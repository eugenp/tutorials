package com.baeldung.opentelemetry;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productNotFound) {
        super(productNotFound);
    }
}
