package com.baeldung.hexagonal.arch.port.service;

import java.util.List;

import com.baeldung.hexagonal.arch.core.domain.Contact;

public interface AddressBookService {

    public void createContact(Contact contact);

    public List<Contact> getAllContacts();

}
