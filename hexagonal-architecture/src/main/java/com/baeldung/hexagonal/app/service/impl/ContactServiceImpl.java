package com.baeldung.hexagonal.app.service.impl;

import com.baeldung.hexagonal.app.model.Contact;
import com.baeldung.hexagonal.app.repository.ContactRepository;
import com.baeldung.hexagonal.app.service.ContactService;

public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void addContact(Contact contact) {
        this.contactRepository.save(contact);
    }

}
