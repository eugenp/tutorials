package com.baeldung.cloud.openfeign.exception;

public class BadRequestException extends Exception{

    private String message;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super();
        this.message = message;
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "BadRequestException: "+message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}