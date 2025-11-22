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

    @Override
    public T findOne(final long id) {
        // The type of the ID is assumed to be Integer/int based on the original usage (intValue()).
        // This is highly dependent on the entity's ID type. Assuming 'id' is a Long/long from IOperations
        // and the Entity's ID is Integer for consistency with the original code.
        return em.find(clazz, id); // Use 'id' directly. JPA will handle the type cast/wrapping.
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
        em.persist(entity);
    }

    @Override
    public T update(final T entity) {
        // The result of merge must be returned, as merge returns the managed entity instance.
        return em.merge(entity);
    }

    @Override
    public void delete(final T entity) {
        // Ensure entity is managed before removal
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    public void deleteById(final long entityId) {
        // Find is sufficient; merge/contains check is done in delete(T entity)
        delete(findOne(entityId));
    }
}
