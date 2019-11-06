package com.baeldung.hexagonal.arch.adapter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.arch.core.domain.Contact;
import com.baeldung.hexagonal.arch.port.service.AddressBookService;

@RestController
@RequestMapping("/contacts")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    public void addContact(@RequestBody Contact contact) {
        addressBookService.createContact(contact);
    }

    @GetMapping
    public List<Contact> getAllContacts() {
        return addressBookService.getAllContacts();
    }

}
