package com.baeldung.errorhandling;

public class CustomRequestAuthException extends RuntimeException {
    public CustomRequestAuthException(String message) {
        super(message);
    }
}
