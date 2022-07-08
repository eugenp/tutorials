package com.baeldung.cloud.openfeign.customizederrorhandling.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
