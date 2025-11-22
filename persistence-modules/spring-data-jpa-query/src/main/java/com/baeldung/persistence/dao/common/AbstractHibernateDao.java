package com.baeldung.persistence.dao.common;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import jakarta.persistence.criteria.CriteriaQuery; // Import needed for type-safe query

@SuppressWarnings("unchecked")
public abstract class AbstractHibernateDao<T extends Serializable> extends AbstractDao<T> implements IOperations<T> {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public T findOne(final long id) {
        // Replaced Session.get() (or load()) with modern byId().getReference() or get()
        return getCurrentSession().byId(clazz).load(id); 
    }

    @Override
    public List<T> findAll() {
        // Used type-safe createQuery with the target class instead of the deprecated untyped list()
        return getCurrentSession().createQuery("from " + clazz.getName(), clazz).getResultList();
    }

    @Override
    public void create(final T entity) {
        Preconditions.checkNotNull(entity);
        // saveOrUpdate() is deprecated/removed in favor of merge() or persist()
        // If entity is transient (new), use persist. If detached (update), use merge.
        // For simplicity and matching original intent, we'll keep the logic that handles both:
        getCurrentSession().merge(entity); 
    }

    @Override
    public T update(final T entity) {
        Preconditions.checkNotNull(entity);
        return getCurrentSession().merge(entity);
    }

    @Override
    public void delete(final T entity) {
        Preconditions.checkNotNull(entity);
        getCurrentSession().remove(entity); // FIX: Use remove() instead of deprecated delete()
    }

    @Override
    public void deleteById(final long entityId) {
        final T entity = findOne(entityId);
        Preconditions.checkState(entity != null);
        delete(entity);
    }

    protected Session getCurrentSession() {
        // This method is generally correct for Spring's declarative transaction management
        return sessionFactory.getCurrentSession();
    }
}
