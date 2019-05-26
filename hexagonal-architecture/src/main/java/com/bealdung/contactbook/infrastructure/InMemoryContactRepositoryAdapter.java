package com.bealdung.contactbook.infrastructure;

import com.bealdung.contactbook.domain.Contact;
import com.bealdung.contactbook.domain.ContactRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class InMemoryContactRepositoryAdapter implements ContactRepository {
    private Collection<Contact> contactList = new ArrayList<>();

    @Override
    public void save(Contact contact) {
        this.contactList.add(contact);
        System.out.println("Contact saved: " + contact);
    }

    @Override
    public Collection<Contact> findByName(String name) {
        return contactList.stream()
                .filter(c -> c.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}
