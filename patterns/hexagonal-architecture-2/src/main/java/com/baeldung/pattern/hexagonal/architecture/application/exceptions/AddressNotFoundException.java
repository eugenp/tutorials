package com.baeldung.pattern.hexagonal.architecture.application.exceptions;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(Long id) {
        super("There was no address with id: " + id);
    }
}
