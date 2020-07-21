package com.architecture.hexagonal.exception;

/**
 * Exception when product is not found
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }

}
