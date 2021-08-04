package com.baeldung.hexagonal_example.domain.ports;

import com.baeldung.hexagonal_example.domain.ContactDTO;

public interface ContactRepository {
    void store(ContactDTO dto);
}
