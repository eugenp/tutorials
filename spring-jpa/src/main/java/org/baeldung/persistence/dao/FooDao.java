package org.baeldung.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.baeldung.persistence.model.Foo;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;

@Repository
public class FooDao implements IFooDao {

    @PersistenceContext
    private EntityManager entityManager;

    public FooDao() {
        super();
    }

    // API

    @Override
    public Foo findOne(final long id) {
        return entityManager.find(Foo.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Foo> findAll() {
        return entityManager.createQuery("from " + Foo.class.getName()).getResultList();
    }

    @Override
    public void create(final Foo entity) {
        Preconditions.checkNotNull(entity);
        entityManager.persist(entity);
    }

    @Override
    public Foo update(final Foo entity) {
        Preconditions.checkNotNull(entity);
        return entityManager.merge(entity);
    }

    @Override
    public void delete(final Foo entity) {
        Preconditions.checkNotNull(entity);
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(final long entityId) {
        final Foo entity = findOne(entityId);
        delete(entity);
    }

}
