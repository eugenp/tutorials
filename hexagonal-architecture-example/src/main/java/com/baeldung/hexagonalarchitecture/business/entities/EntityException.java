package com.baeldung.hexagonalarchitecture.business.entities;

class EntityException extends RuntimeException {
    EntityException(final String message) {
        super(message);
    }
}
