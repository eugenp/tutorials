package com.bealdung.contactbook;

import com.bealdung.contactbook.application.ConsoleContactUIAdapter;
import com.bealdung.contactbook.domain.Contact;
import com.bealdung.contactbook.domain.ContactManager;
import com.bealdung.contactbook.domain.ContactRepositoryPort;
import com.bealdung.contactbook.infrastructure.InMemoryContactRepositoryAdapter;

import java.util.Collection;

public class ContactBookRunner {
    public static void main(String[] args) {
        // initialize the system
        ContactRepositoryPort contactRepositoryPort = new InMemoryContactRepositoryAdapter();
        ContactManager contactManager = new ContactManager(contactRepositoryPort);
        ConsoleContactUIAdapter contactUIAdapter = new ConsoleContactUIAdapter(contactManager);

        // Save new Contacts (e.g. input can be read from system console)
        contactUIAdapter.saveContact(Contact.builder()
          .name("John Sherman")
          .email("john@sherman.com")
          .build());
        contactUIAdapter.saveContact(Contact.builder()
          .name("Peter Smith")
          .email("peter@smith.com")
          .build());

        // Search contacts by name
        Collection<Contact> searchResults = contactUIAdapter.searchContactsByName("john");
        System.out.println("Found contact with name 'john': " + searchResults);
    }
}
