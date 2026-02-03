package com.baeldung.detachentity.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class DetachableRepositoryImpl<T> implements DetachableRepository<T> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void detach(T entity) {
        if(entity != null) {
            entityManager.detach(entity);
        }
    }
}
