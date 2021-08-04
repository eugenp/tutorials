package com.baeldung.hexagonal_example.domain.ports;

import com.baeldung.hexagonal_example.domain.ContactCommand;
import com.baeldung.hexagonal_example.domain.ContactDTO;

public interface ContactApiPort {
    ContactDTO create(ContactCommand command);
}
