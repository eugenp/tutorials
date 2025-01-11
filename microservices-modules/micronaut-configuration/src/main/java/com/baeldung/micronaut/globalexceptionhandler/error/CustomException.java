package com.baeldung.micronaut.globalexceptionhandler.error;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
