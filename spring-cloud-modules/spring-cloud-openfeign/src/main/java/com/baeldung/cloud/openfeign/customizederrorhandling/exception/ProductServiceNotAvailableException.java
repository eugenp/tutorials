package com.baeldung.cloud.openfeign.customizederrorhandling.exception;

public class ProductServiceNotAvailableException extends RuntimeException {

    public ProductServiceNotAvailableException(String message) {
        super(message);
    }

    public ProductServiceNotAvailableException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
