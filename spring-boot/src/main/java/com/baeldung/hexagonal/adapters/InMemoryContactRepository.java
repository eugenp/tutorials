package com.baeldung.hexagonal.adapters;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.baeldung.hexagonal.models.Contact;
import com.baeldung.hexagonal.ports.ContactRepository;

public class InMemoryContactRepository implements ContactRepository {

    private ConcurrentHashMap<String, Contact> store = new ConcurrentHashMap<>();

    public InMemoryContactRepository() {
    }

    @Override
    public Contact create(Contact contact) {
        contact.setId(generateId());
        store.put(contact.getId(), contact);
        return contact;
    }

    @Override
    public Contact read(String id) {
        return store.get(id);
    }

    @Override
    public Contact update(Contact contact) {
        store.put(contact.getId(), contact);
        return contact;
    }

    @Override
    public void delete(String id) {
        store.remove(id);
    }

    @Override
    public Collection<Contact> list() {
        return store.values();
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

}
