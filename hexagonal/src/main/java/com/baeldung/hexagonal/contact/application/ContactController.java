package com.baeldung.hexagonal.contact.application;

import java.util.UUID;

import com.baeldung.hexagonal.contact.domain.model.NewContactCommand;
import com.baeldung.hexagonal.contact.domain.ports.incoming.CreateNewContact;

public class ContactController {

    private CreateNewContact createNewContact;

    public ContactController(CreateNewContact createNewContact) {
        this.createNewContact = createNewContact;
    }

    public String createNewContact(NewContactCommand command) {
        UUID contactUUID = createNewContact.handle(command);
        return String.format("Created Contact with identifier %s", contactUUID.toString());
    }
}
