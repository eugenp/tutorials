package com.baeldung.domain;

public class UnableToBookException extends RuntimeException {
    public UnableToBookException() {
        super("Unable to book");
    }
}
