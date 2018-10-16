package com.hexagonal.demo.Adapters;

import com.hexagonal.demo.domain.Area;
import com.hexagonal.demo.ports.secondary.AreaPortRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AreaRepositorySecondaryAdapter implements AreaPortRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(Object obj) {
        entityManager.persist(obj);
    }

    @Override
    public Area getArea(String zipCode) {
        return entityManager.find(Area.class, zipCode);
    }
}
