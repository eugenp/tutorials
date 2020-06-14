package com.baeldung.hexagonal.domain;

class DomainException extends RuntimeException {
    DomainException(final String message) {
        super(message);
    }
}
