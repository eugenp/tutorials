package com.baeldung.hexagonal_example.domain;

import com.baeldung.hexagonal_example.domain.ports.ContactRepository;

class FakeContactRepository implements ContactRepository {
    @Override
    public void store(ContactDTO dto) {
        // do nothing
    }
}
