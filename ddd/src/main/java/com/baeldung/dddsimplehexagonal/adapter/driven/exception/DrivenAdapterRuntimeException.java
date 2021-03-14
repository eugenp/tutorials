package com.baeldung.dddsimplehexagonal.adapter.driven.exception;

public class DrivenAdapterRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 4647218053611560302L;

    public DrivenAdapterRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DrivenAdapterRuntimeException(String message) {
        super(message);
    }
}
