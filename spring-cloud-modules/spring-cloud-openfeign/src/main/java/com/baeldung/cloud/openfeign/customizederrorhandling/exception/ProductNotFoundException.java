package com.baeldung.cloud.openfeign.customizederrorhandling.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
