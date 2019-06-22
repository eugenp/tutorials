package com.baeldung.hexagonal.core.domain.exception;

public class PostAlreadyExistsException extends PostServiceException {

    public PostAlreadyExistsException(String message) {
        super(message);
    }

    public PostAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
