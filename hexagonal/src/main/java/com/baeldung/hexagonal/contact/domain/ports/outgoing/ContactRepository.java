package com.baeldung.hexagonal.contact.domain.ports.outgoing;

import java.util.UUID;

import com.baeldung.hexagonal.contact.domain.model.NewContactCommand;

public interface ContactRepository {

    UUID create(NewContactCommand command);
}
