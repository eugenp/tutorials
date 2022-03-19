package com.baeldung.hexagonal.architecture.example.service.exception;

public class NoDataFoundException extends RuntimeException {

    public NoDataFoundException(String message) {
        super(message);
    }
}
