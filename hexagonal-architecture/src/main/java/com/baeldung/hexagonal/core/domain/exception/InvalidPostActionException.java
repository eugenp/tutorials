package com.baeldung.hexagonal.core.domain.exception;

public class InvalidPostActionException extends RuntimeException {

    public InvalidPostActionException(String message) {
        super(message);
    }

    public InvalidPostActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
