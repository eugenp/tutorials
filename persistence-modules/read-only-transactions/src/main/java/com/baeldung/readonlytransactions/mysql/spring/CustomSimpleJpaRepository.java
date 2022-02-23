package com.baeldung.readonlytransactions.mysql.spring;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class CustomSimpleJpaRepository<ENTITY, ID> extends SimpleJpaRepository<ENTITY, ID> {

    private EntityManager entityManager;
    private JpaEntityInformation<ENTITY, ?> entityInformation;

    public CustomSimpleJpaRepository(JpaEntityInformation<ENTITY, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    public CustomSimpleJpaRepository(Class<ENTITY> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public ENTITY getById(ID id) {
        //This is only for the throughput test, to ignore case and do not use transactions explicitly neither caching
        entityManager.clear();
        entityManager.unwrap(Session.class).setCacheMode(CacheMode.IGNORE);
        return entityManager.find(entityInformation.getJavaType(), id);
    }
}
