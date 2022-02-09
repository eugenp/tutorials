package com.baeldung.webclient.status.exception;

public class CustomBadRequestException extends Exception {
    public CustomBadRequestException(String message) {
        super(message);
    }
}
