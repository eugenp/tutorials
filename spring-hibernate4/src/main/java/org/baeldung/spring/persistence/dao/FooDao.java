package org.baeldung.spring.persistence.dao;

import java.util.List;

import org.baeldung.spring.persistence.model.Foo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;

@Repository
public class FooDao implements IFooDao {

    @Autowired
    private SessionFactory sessionFactory;

    public FooDao() {
        super();
    }

    // API

    @Override
    public Foo findOne(final Long id) {
        Preconditions.checkArgument(id != null);
        return (Foo) getCurrentSession().get(Foo.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Foo> findAll() {
        return getCurrentSession().createQuery("from " + Foo.class.getName()).list();
    }

    @Override
    public void create(final Foo entity) {
        Preconditions.checkNotNull(entity);
        getCurrentSession().persist(entity);
    }

    @Override
    public void update(final Foo entity) {
        Preconditions.checkNotNull(entity);
        getCurrentSession().merge(entity);
    }

    @Override
    public void delete(final Foo entity) {
        Preconditions.checkNotNull(entity);
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteById(final Long entityId) {
        final Foo entity = findOne(entityId);
        Preconditions.checkState(entity != null);
        delete(entity);
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
