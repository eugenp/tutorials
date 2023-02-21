package com.baeldung.core.customizederrorhandling.exception;

public class ProductServiceNotAvailableException extends RuntimeException {

    public ProductServiceNotAvailableException(String message) {
        super(message);
    }
}
