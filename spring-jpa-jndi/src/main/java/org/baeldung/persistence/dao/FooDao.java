package org.baeldung.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.baeldung.persistence.model.Foo;
import org.springframework.stereotype.Repository;

@Repository
public class FooDao {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Foo> findAll() {
        return entityManager.createQuery("from " + Foo.class.getName()).getResultList();
    }

}
