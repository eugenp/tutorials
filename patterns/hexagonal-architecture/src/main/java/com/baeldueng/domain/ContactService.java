package com.baeldueng.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@AllArgsConstructor
public class ContactService {
    ContactRepository contactRepository;

    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }
}
