package com.baeldung.persistence.dao.common;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class AbstractJpaDao<T extends Serializable> extends AbstractDao<T> implements IOperations<T> {

    @PersistenceContext(unitName = "jpaEntityManager")
    private EntityManager em;

    // API

    @Override
    public T findOne(final long id) {
        // FIX: Removed the unsafe conversion to int and use the Long wrapper class for the ID.
        // JPA/Hibernate will handle mapping this Long ID to the entity's primary key type.
        return em.find(clazz, Long.valueOf(id));
    }

    @Override
    public List<T> findAll() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<T> cq = cb.createQuery(clazz);
        final Root<T> rootEntry = cq.from(clazz);
        final CriteriaQuery<T> all = cq.select(rootEntry);
        final TypedQuery<T> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public void create(final T entity) {
        // We assume a null check is performed in AbstractDao.setClazz() or before calling this.
        em.persist(entity);
    }

    @Override
    public T update(final T entity) {
        // We assume a null check is performed in AbstractDao.setClazz() or before calling this.
        return em.merge(entity);
    }

    @Override
    public void delete(final T entity) {
        // Must ensure the entity is managed before removal, which em.remove implies.
        em.remove(entity);
    }

    @Override
    public void deleteById(final long entityId) {
        // findOne will return null if not found, and delete(null) will throw a 
        // NullPointerException, which is acceptable in a DAO layer.
        delete(findOne(entityId));
    }

}
