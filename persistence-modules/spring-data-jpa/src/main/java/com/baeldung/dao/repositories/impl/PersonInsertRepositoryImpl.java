package com.baeldung.dao.repositories.impl;

import com.baeldung.dao.repositories.PersonInsertRepository;
import com.baeldung.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class PersonInsertRepositoryImpl implements PersonInsertRepository {

    private EntityManager entityManager;

    @Autowired
    public PersonInsertRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void insert(Person person) {
        entityManager.createNativeQuery("INSERT INTO person (id,first_name, last_name) VALUES (?,?,?)")
          .setParameter(1, person.getId())
          .setParameter(2, person.getFirstName())
          .setParameter(3, person.getLastName())
          .executeUpdate();
    }
}
