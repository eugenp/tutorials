package com.bealdung.contactbook.application;

import com.bealdung.contactbook.domain.Contact;
import com.bealdung.contactbook.domain.ContactAdministrator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsoleContactAdministratorAdapter {
    private final ContactAdministrator contactManager;

    public void saveContact(Contact contact) {
        this.contactManager.save(contact);
    }

}
