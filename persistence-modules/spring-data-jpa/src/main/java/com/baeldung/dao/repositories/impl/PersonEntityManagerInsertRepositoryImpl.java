package com.baeldung.dao.repositories.impl;

import com.baeldung.dao.repositories.PersonEntityManagerInsertRepository;
import com.baeldung.domain.Person;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class PersonEntityManagerInsertRepositoryImpl implements PersonEntityManagerInsertRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insert(Person person) {
        this.entityManager.persist(person);
    }
}

