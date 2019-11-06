package com.baeldung.hexagonal.arch.port.repo;

import java.util.List;

import com.baeldung.hexagonal.arch.core.domain.Contact;

public interface ContactRepository {

    void createContact(Contact contact);

    List<Contact> getAllContacts();

}
