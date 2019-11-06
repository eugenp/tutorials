package com.baeldung.architecture.hexagonal.personal.library.core.domain.exception;

public class BookValidationException extends RuntimeException{

    public BookValidationException(String message) {
        super(message);
    }
}
