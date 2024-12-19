package com.baeldung.hibernate.cache.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractJpaDAO<T extends Serializable> {

    private Class<T> clazz;

    @PersistenceContext
    private EntityManager entityManager;

    public final void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T findOne(final long id) {
        return entityManager.find(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("from " + clazz.getName()).getResultList();
    }

    public void create(final T entity) {
        entityManager.persist(entity);
    }

    public T update(final T entity) {
        return entityManager.merge(entity);
    }

    public void delete(final T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(final long entityId) {
        final T entity = findOne(entityId);
        delete(entity);
    }

    public long countAllRowsUsingHibernateCriteria() {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(clazz);
        criteria.setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();
        return count != null ? count : 0L;
    }

    public long getFooCountByBarNameUsingHibernateCriteria(String barName) {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(clazz);
        criteria.createAlias("bar", "b");
        criteria.add(Restrictions.eq("b.name", barName));
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    public long getFooCountByBarNameAndFooNameUsingHibernateCriteria(String barName, String fooName) {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(clazz);
        criteria.createAlias("bar", "b");
        criteria.add(Restrictions.eq("b.name", barName));
        criteria.add(Restrictions.eq("name", fooName));
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
}