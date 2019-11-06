package com.baeldung.hexagonal.arch.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.arch.core.domain.Contact;
import com.baeldung.hexagonal.arch.port.repo.ContactRepository;
import com.baeldung.hexagonal.arch.port.service.AddressBookService;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    ContactRepository contactRepository;

    @Override
    public void createContact(Contact contact) {
        contactRepository.createContact(contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.getAllContacts();
    }

}
