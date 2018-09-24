package com.baeldung.hexagonal.ports;

import java.util.Collection;

import com.baeldung.hexagonal.models.Contact;

public interface ContactService {

    Contact create(Contact contact);
    Contact read(String id);
    Contact update(Contact contact);
    void delete(String id);
    Collection<Contact> list();

}
