package com.baeldung.errorhandling;

public class RateLimitRequestException extends RuntimeException {
    public RateLimitRequestException(String message) {
        super(message);
    }
}
