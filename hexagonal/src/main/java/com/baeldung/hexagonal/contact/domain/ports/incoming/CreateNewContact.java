package com.baeldung.hexagonal.contact.domain.ports.incoming;

import java.util.UUID;

import com.baeldung.hexagonal.contact.domain.model.NewContactCommand;

public interface CreateNewContact {

    public UUID handle(NewContactCommand contact);
}
