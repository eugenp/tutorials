package com.baeldung.patterns.hexagonal_quick.exception;

public class LibraryException extends RuntimeException {

    public LibraryException(String message) {
        super(message);
    }
}
