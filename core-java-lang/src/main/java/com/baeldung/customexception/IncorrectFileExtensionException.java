package com.baeldung.customexception;

public class IncorrectFileExtensionException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public IncorrectFileExtensionException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
