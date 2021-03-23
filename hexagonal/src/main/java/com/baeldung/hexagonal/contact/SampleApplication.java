package com.baeldung.hexagonal.contact;

import com.baeldung.hexagonal.contact.application.ContactController;
import com.baeldung.hexagonal.contact.domain.ContactService;
import com.baeldung.hexagonal.contact.domain.model.NewContactCommand;
import com.baeldung.hexagonal.contact.domain.ports.incoming.CreateNewContact;
import com.baeldung.hexagonal.contact.domain.ports.outgoing.ContactRepository;
import com.baeldung.hexagonal.contact.infrastructure.InMemoryContactRepositoryAdapter;

public class SampleApplication {

    public static void main(String[] args) {
        ContactRepository contactRepository = new InMemoryContactRepositoryAdapter();
        CreateNewContact createNewContact = new ContactService(contactRepository);
        ContactController contactController = new ContactController(createNewContact);

        NewContactCommand newContactCommand = new NewContactCommand("name", "000-000-000");
        String result = contactController.createNewContact(newContactCommand);
        System.out.println(result);
    }

}
