package com.baeldung.dao.repositories.impl;

import com.baeldung.domain.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class PersonInsertRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertWithQuery(Person person) {
        entityManager.createNativeQuery("INSERT INTO person (id, first_name, last_name) VALUES (?,?,?)")
          .setParameter(1, person.getId())
          .setParameter(2, person.getFirstName())
          .setParameter(3, person.getLastName())
          .executeUpdate();
    }

    @Transactional
    public void insertWithEntityManager(Person person) {
        this.entityManager.persist(person);
    }

}
