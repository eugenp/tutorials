package com.baeldung.hexagonal_example.domain;

import com.baeldung.hexagonal_example.domain.ports.ContactApiPort;
import com.baeldung.hexagonal_example.domain.ports.ContactRepository;

class ContactApplication implements ContactApiPort {

    private ContactRepository repository;

    public ContactApplication(ContactRepository repository) {
        this.repository = repository;
    }

    public ContactDTO create(ContactCommand command) {
        Contact contact = new Contact(command.getName(), command.getEmail(), command.getMobilePhone());

        final ContactDTO dto = contact.toDTO();
        repository.store(dto);
        return dto;
    }
}
