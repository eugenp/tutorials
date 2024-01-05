package com.baeldung.spring.insertableonly.entitymanager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class PersistableEntityManagerBookRepositoryImpl implements PersistableEntityManagerBookRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public <S extends EntityManagerBook> S persist(S entity) {
        entityManager.persist(entity);
        return entity;
    }

}
