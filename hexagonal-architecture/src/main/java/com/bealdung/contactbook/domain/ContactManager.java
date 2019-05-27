package com.bealdung.contactbook.domain;

import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class ContactManager implements ContactUIPort {

    private final ContactRepositoryPort contactRepositoryPort;

    @Override
    public void save(Contact contact) {
        if (valid(contact)) {  // business core logic
            this.contactRepositoryPort.save(contact);
        } else {
            throw new RuntimeException("Contact is not valid!");
        }
    }

    @Override
    public Collection<Contact> findByName(String name) {
        return this.contactRepositoryPort.findByName(name);
    }

    // contact must contain name at least
    private boolean valid(Contact contact) {
        return contact != null && contact.getName() != null;
    }

}
