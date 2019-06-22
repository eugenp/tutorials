package com.baeldung.hexagonal.core.domain.exception;

public class PostServiceException extends RuntimeException {

    public PostServiceException(String message) {
        super(message);
    }

    public PostServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
