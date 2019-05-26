package com.bealdung.contactbook.domain;

import lombok.RequiredArgsConstructor;

import java.util.Collection;

// Note: ContactManager implements both exposed interface "ports"
//       and depends on ContactRepository "port"
@RequiredArgsConstructor
public class ContactManager implements ContactAdministrator, ContactSearcher {

    private final ContactRepository contactRepository;

    @Override
    public void save(Contact contact) {
        if (valid(contact)) {  // business core logic
            this.contactRepository.save(contact);
        } else {
            throw new ContactNotValidException();
        }
    }

    @Override
    public Collection<Contact> findByName(String name) {
        return this.contactRepository.findByName(name);
    }

    // contact must contain name at least
    private boolean valid(Contact contact) {
        return contact != null && contact.getName() != null;
    }

}
