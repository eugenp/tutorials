package com.baeldung.cloud.openfeign.customizederrorhandling.exception;

//TODO keep
public class ProductServiceNotAvailableException extends RuntimeException {

    public ProductServiceNotAvailableException(String message) {
        super(message);
    }
}
