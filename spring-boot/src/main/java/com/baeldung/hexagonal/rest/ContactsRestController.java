package com.baeldung.hexagonal.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.models.Contact;
import com.baeldung.hexagonal.ports.ContactService;

@RestController
public class ContactsRestController {

    private ContactService contactService;

    @Autowired
    public ContactsRestController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    public ResponseEntity<Contact> create(@RequestBody Contact contact) {
        contact = contactService.create(contact);
        return contact != null ? ResponseEntity.ok(contact) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/contacts/{id}", method = RequestMethod.GET)
    public ResponseEntity<Contact> read(@PathVariable String id) {
        Contact contact = contactService.read(id);
        return contact != null ? ResponseEntity.ok(contact) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.PUT)
    public ResponseEntity<Contact> update(@RequestBody Contact contact) {
        contact = contactService.update(contact);
        return contact != null ? ResponseEntity.ok(contact) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/contacts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) {
        contactService.delete(id);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public ResponseEntity<Collection<Contact>> list() {
        return ResponseEntity.ok(contactService.list());
    }

}
