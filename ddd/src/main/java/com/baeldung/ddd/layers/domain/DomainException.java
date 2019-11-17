package com.baeldung.ddd.layers.domain;

class DomainException extends RuntimeException {
    DomainException(final String message) {
        super(message);
    }
}
