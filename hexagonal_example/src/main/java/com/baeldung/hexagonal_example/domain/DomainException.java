package com.baeldung.hexagonal_example.domain;

public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
