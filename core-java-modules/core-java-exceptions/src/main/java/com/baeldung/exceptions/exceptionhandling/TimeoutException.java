package com.baeldung.exceptions.exceptionhandling;

public class TimeoutException extends Exception {

    public TimeoutException(String message) {
        super(message);
    }
}
