package com.baeldueng.infrastructure;

import com.baeldueng.domain.Contact;
import com.baeldueng.domain.ContactRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ContactRepositoryImpl implements ContactRepository {

    private Map<String, Contact> contactStore = new HashMap<>();

    @Override
    public Contact save(Contact contact) {
        contactStore.put(contact.getId(), contact);
        return contact;
    }

    @Override
    public Contact getById(String id) {
        return contactStore.get(id);
    }
}
