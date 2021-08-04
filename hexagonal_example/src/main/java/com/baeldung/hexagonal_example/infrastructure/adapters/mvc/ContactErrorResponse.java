package com.baeldung.hexagonal_example.infrastructure.adapters.mvc;

import com.baeldung.hexagonal_example.domain.DomainException;

class ContactErrorResponse {

    private String errorMessage;

    public static ContactErrorResponse from(DomainException ex) {
        return new ContactErrorResponse(ex.getMessage());
    }

    public ContactErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
