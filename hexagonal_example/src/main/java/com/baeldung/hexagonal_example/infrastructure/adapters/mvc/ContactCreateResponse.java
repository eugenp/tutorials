package com.baeldung.hexagonal_example.infrastructure.adapters.mvc;

import com.baeldung.hexagonal_example.domain.ContactDTO;

class ContactCreateResponse {

    private String id;

    public static ContactCreateResponse from(ContactDTO dto) {
        return new ContactCreateResponse(dto.getName());
    }

    public ContactCreateResponse(String name) {
        this.id = name;
    }

    public String getId() {
        return id;
    }
}
