package com.baeldung.dddsimplehexagonal.adapter.driver.exception;

public class DriverAdapterRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -6755840050401492598L;

    public DriverAdapterRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DriverAdapterRuntimeException(String message) {
        super(message);
    }
}
