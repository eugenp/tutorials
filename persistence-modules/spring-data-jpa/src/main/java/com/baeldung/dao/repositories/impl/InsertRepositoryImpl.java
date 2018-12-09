package com.baeldung.dao.repositories.impl;

import com.baeldung.dao.repositories.InsertRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class InsertRepositoryImpl<T> implements InsertRepository<T> {
    private final EntityManager em;

    public InsertRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    @Transactional
    public <S extends T> void insert(S entity) {
        this.em.persist(entity);
    }
}
