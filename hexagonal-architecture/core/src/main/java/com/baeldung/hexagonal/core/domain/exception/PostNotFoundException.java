package com.baeldung.hexagonal.core.domain.exception;

public class PostNotFoundException extends PostServiceException {

    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
