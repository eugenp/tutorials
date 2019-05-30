package com.baeldung.hexagonal.jpa;

import com.baeldung.hexagonal.domain.Person;
import com.baeldung.hexagonal.persistence.PersonRepository;
import javax.inject.Inject;
import javax.persistence.EntityManager;

class PersonEntityRepository implements PersonRepository {

    private final EntityManager em;

    @Inject
    PersonEntityRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Person getById(int id) {
        return em.find(PersonEntity.class, id);
    }

    @Override
    public void save(Person person) {
        em.persist(person);
    }
}
