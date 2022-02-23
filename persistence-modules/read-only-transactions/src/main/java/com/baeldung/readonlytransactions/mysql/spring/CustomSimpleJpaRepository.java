package com.baeldung.readonlytransactions.mysql.spring;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class CustomSimpleJpaRepository<T, ID> extends SimpleJpaRepository<T, ID> {

    private EntityManager entityManager;
    private JpaEntityInformation<T, ?> entityInformation;

    public CustomSimpleJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    public CustomSimpleJpaRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public T getById(ID id) {
        //This is only for the throughput test, to ignore case and do not use transactions explicitly neither caching
        entityManager.clear();
        entityManager.unwrap(Session.class).setCacheMode(CacheMode.IGNORE);
        return entityManager.find(entityInformation.getJavaType(), id);
    }
}
