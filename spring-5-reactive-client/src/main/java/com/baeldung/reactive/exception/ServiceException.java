package com.baeldung.reactive.exception;

public class ServiceException extends RuntimeException{

    public ServiceException(String message) {
        super(message);
    }
}
