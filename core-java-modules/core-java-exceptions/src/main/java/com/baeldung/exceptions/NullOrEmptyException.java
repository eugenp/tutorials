package com.baeldung.exceptions;

public class NullOrEmptyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NullOrEmptyException(String errorMessage) {
        super(errorMessage);
    }

    public NullOrEmptyException(String errorMessage, Throwable thr) {
        super(errorMessage, thr);
    }
}