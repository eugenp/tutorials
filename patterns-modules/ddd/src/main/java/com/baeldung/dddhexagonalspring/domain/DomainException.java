package com.baeldung.dddhexagonalspring.domain;

class DomainException extends RuntimeException {
    DomainException(final String message) {
        super(message);
    }
}
