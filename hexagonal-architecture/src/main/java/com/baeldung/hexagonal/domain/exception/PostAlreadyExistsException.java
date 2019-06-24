package com.baeldung.hexagonal.domain.exception;

public class PostAlreadyExistsException extends RuntimeException {

    public PostAlreadyExistsException(String message) {
        super(message);
    }

    public PostAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
