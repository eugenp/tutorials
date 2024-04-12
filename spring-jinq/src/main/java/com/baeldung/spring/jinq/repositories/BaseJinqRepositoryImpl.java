package com.baeldung.spring.jinq.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseJinqRepositoryImpl<T> {
    @Autowired
    private JinqJPAStreamProvider jinqDataProvider;

    @PersistenceContext
    private EntityManager entityManager;

    protected abstract Class<T> entityType();

    public JPAJinqStream<T> stream() {
        return streamOf(entityType());
    }

    protected <U> JPAJinqStream<U> streamOf(Class<U> clazz) {
        return jinqDataProvider.streamAll(entityManager, clazz);
    }
}
