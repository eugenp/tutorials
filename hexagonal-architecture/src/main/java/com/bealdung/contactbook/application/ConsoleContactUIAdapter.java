package com.bealdung.contactbook.application;

import com.bealdung.contactbook.domain.Contact;
import com.bealdung.contactbook.domain.ContactUIPort;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class ConsoleContactUIAdapter {
    private final ContactUIPort contactUIPort;

    public void saveContact(Contact contact) {
        this.contactUIPort.save(contact);
    }

    public Collection<Contact> searchContactsByName(String name) {
        return this.contactUIPort.findByName(name);
    }
}
