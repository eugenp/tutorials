package com.bealdung.contactbook;

import com.bealdung.contactbook.application.ConsoleContactAdministratorAdapter;
import com.bealdung.contactbook.application.ConsoleContactSearcherAdapter;
import com.bealdung.contactbook.domain.Contact;
import com.bealdung.contactbook.domain.ContactAdministrator;
import com.bealdung.contactbook.domain.ContactManager;
import com.bealdung.contactbook.domain.ContactRepository;
import com.bealdung.contactbook.infrastructure.InMemoryContactRepositoryAdapter;

import java.util.Collection;

public class ContactBookRunner {
    public static void main(String[] args) {
        // initialize the system (dependency inversion principle)
        ContactRepository contactRepository = new InMemoryContactRepositoryAdapter();
        ContactManager contactManager = new ContactManager(contactRepository);

        // application adapters depend on different domain interfaces (interface segregation principle)
        ConsoleContactAdministratorAdapter administratorAdapter = new ConsoleContactAdministratorAdapter(contactManager);
        ConsoleContactSearcherAdapter searcherAdapter = new ConsoleContactSearcherAdapter(contactManager);

        // Save new Contacts (e.g. input can be read from system console)
        administratorAdapter.saveContact(Contact.builder()
                .name("John Sherman")
                .email("john@sherman.com")
                .build());

        administratorAdapter.saveContact(Contact.builder()
                .name("Peter Smith")
                .email("peter@smith.com")
                .build());

        // Search contacts by name
        Collection<Contact> searchResults = searcherAdapter.searchContactsByName("john");
        System.out.println("Found contact with name 'john': " + searchResults);
    }
}
