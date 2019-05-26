package com.bealdung.contactbook.application;

import com.bealdung.contactbook.domain.Contact;
import com.bealdung.contactbook.domain.ContactAdministrator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsoleContactAdministratorAdapter {
    private final ContactAdministrator contactAdministrator;

    public void saveContact(Contact contact) {
        this.contactAdministrator.save(contact);
    }

}
