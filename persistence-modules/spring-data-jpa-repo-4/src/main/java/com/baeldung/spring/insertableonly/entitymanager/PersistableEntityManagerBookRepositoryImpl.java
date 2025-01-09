package com.baeldung.spring.insertableonly.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class PersistableEntityManagerBookRepositoryImpl<S> implements PersistableEntityManagerBookRepository<S> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public S persist(S entity) {
        entityManager.persist(entity);
        return entity;
    }

}
