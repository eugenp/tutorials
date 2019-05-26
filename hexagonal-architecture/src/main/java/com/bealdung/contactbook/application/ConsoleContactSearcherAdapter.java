package com.bealdung.contactbook.application;

import com.bealdung.contactbook.domain.Contact;
import com.bealdung.contactbook.domain.ContactSearcher;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class ConsoleContactSearcherAdapter {
    private final ContactSearcher contactSearcher;

    public Collection<Contact> searchContactsByName(String name) {
        return this.contactSearcher.findByName(name);
    }
}
