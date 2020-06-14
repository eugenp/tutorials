package com.baeldung.hexagonal.infrastructure.repository;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

}
