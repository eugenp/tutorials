package com.baeldung.hexagonal.architecture.adapters.driven;

import com.baeldung.hexagonal.architecture.core.domain.Person;
import com.baeldung.hexagonal.architecture.core.ports.outbound.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;

@Repository
@AllArgsConstructor
public class PersonPersistenceAdapter implements PersonRepository {
    private PersonJpaRepository jpaRepository;

    @Override
    public Person findById(Long id) {
        PersonJpaEntity jpaEntity = jpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return map(jpaEntity);
    }

    private Person map(PersonJpaEntity entity) {
        return new Person(entity.getId(), entity.getName());
    }
}
