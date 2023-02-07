package com.baeldung.cloud.openfeign.customizederrorhandling.exception;

//TODO keep
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
