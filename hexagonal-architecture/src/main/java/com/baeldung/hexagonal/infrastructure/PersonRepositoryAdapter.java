package com.baeldung.hexagonal.infrastructure;

import com.baeldung.hexagonal.domain.Person;
import com.baeldung.hexagonal.domain.PersonRepositoryPort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
class PersonRepositoryAdapter implements PersonRepositoryPort {

    @PersistenceContext private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Person person) {
        entityManager.persist(person);
    }

    @Override
    @Transactional
    public Optional<Person> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(Person.class, id));
    }
}
