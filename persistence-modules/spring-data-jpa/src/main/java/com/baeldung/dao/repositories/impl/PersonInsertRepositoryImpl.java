package com.baeldung.dao.repositories.impl;

import com.baeldung.dao.repositories.PersonInsertRepository;
import com.baeldung.domain.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class PersonInsertRepositoryImpl implements PersonInsertRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insert(Person person) {
        entityManager.createNativeQuery("INSERT INTO person (id,first_name, last_name) VALUES (?,?,?)")
          .setParameter(1, person.getId())
          .setParameter(2, person.getFirstName())
          .setParameter(3, person.getLastName())
          .executeUpdate();
    }
}
