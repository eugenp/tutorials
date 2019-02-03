package com.baeldung.cloud.openfeign.exception;


public class NotFoundException extends Exception{

    private String message;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super();
        this.message = message;
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "NotFoundException: "+message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
