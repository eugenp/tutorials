package com.baeldung.persistence.dao.common;

import java.io.Serializable;
import java.util.List;
import java.util.Objects; // FIX: Import standard Java utility for null checks

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unchecked")
public abstract class AbstractHibernateDao<T extends Serializable> extends AbstractDao<T> implements IOperations<T> {

    @Autowired
    protected SessionFactory sessionFactory;

    // API

    @Override
    public T findOne(final long id) {
        // Use getIdentifier() for find by ID
        return (T) getCurrentSession().get(clazz, id);
    }

    @Override
    public List<T> findAll() {
        // Use createQuery() without deprecated .list()
        return getCurrentSession().createQuery("from " + clazz.getName(), clazz).getResultList();
    }

    @Override
    public void create(final T entity) {
        // FIX: Replaced Preconditions.checkNotNull with Objects.requireNonNull
        Objects.requireNonNull(entity);
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public T update(final T entity) {
        // FIX: Replaced Preconditions.checkNotNull with Objects.requireNonNull
        Objects.requireNonNull(entity);
        return (T) getCurrentSession().merge(entity);
    }

    @Override
    public void delete(final T entity) {
        // FIX: Replaced Preconditions.checkNotNull with Objects.requireNonNull
        Objects.requireNonNull(entity);
        getCurrentSession().remove(entity); // Changed delete to remove (modern JPA/Hibernate)
    }

    @Override
    public void deleteById(final long entityId) {
        final T entity = findOne(entityId);
        // FIX: Replaced Preconditions.checkState with standard Java null check/exception
        if (entity == null) {
            throw new IllegalStateException("Entity not found for deletion: ID " + entityId);
        }
        delete(entity);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
