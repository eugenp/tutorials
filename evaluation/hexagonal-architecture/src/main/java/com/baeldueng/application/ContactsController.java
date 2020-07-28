package com.baeldueng.application;

import com.baeldueng.domain.Contact;
import com.baeldueng.domain.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ContactsController {

    ContactService contactService;

    @PostMapping("/contacts")
    public Contact createOrder(@RequestBody Contact contact) {
        return contactService.createContact(contact);
    }
}
