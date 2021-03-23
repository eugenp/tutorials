package com.baeldung.hexagonal.contact.domain;

import java.util.UUID;

import com.baeldung.hexagonal.contact.domain.model.NewContactCommand;
import com.baeldung.hexagonal.contact.domain.ports.incoming.CreateNewContact;
import com.baeldung.hexagonal.contact.domain.ports.outgoing.ContactRepository;

public class ContactService implements CreateNewContact {

    private ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public UUID handle(NewContactCommand contact) {
        UUID contactUUID = contactRepository.create(contact);
        return contactUUID;
    }
}
