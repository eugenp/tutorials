package com.baeldung.exceptions;

public class IncorrectFileNameException extends Exception {
    private static final long serialVersionUID = 1L;

    public IncorrectFileNameException(String errorMessage) {
        super(errorMessage);
    }

    public IncorrectFileNameException(String errorMessage, Throwable thr) {
        super(errorMessage, thr);
    }
}