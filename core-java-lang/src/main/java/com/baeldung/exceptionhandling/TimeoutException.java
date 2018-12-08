package com.baeldung.exceptionhandling;

public class TimeoutException extends Exception {

    public TimeoutException(String message) {
        super(message);
    }
}
