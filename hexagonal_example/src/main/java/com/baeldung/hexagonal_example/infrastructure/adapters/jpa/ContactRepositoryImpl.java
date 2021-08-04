package com.baeldung.hexagonal_example.infrastructure.adapters.jpa;

import com.baeldung.hexagonal_example.domain.ContactDTO;
import com.baeldung.hexagonal_example.domain.ports.ContactRepository;
import org.springframework.stereotype.Repository;

@Repository
class ContactRepositoryImpl implements ContactRepository {

    private JpaContactRepository repository;

    public ContactRepositoryImpl(JpaContactRepository repository) {
        this.repository = repository;
    }

    @Override
    public void store(ContactDTO dto) {
        repository.save(ContactEntity.from(dto));
    }
}
