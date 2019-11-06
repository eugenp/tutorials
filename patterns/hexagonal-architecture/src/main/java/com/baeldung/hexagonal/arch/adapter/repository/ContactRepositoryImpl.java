package com.baeldung.hexagonal.arch.adapter.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.arch.core.domain.Contact;
import com.baeldung.hexagonal.arch.port.repo.ContactRepository;

@Repository
public class ContactRepositoryImpl implements ContactRepository {

    private Map<String, Contact> contactsRepo = new HashMap<String, Contact>();

    @Override
    public void createContact(Contact contact) {
        contactsRepo.put(contact.getName(), contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactsRepo.values()
            .stream()
            .collect(Collectors.toList());
    }

}
