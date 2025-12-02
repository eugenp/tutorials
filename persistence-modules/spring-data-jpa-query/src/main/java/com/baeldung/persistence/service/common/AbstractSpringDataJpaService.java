package com.baeldung.persistence.service.common;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; // FIX: Import standard Java Stream API

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.persistence.dao.common.IOperations;
// FIX: Removed Guava import (com.google.common.collect.Lists)

@Transactional(value = "jpaTransactionManager")
public abstract class AbstractSpringDataJpaService<T extends Serializable> implements IOperations<T> {

    @Override
    public T findOne(final long id) {
        // FIX: Replaced opt.get() with opt.orElse(null) to avoid throwing NoSuchElementException
        // This aligns with the non-Optional contract of IOperations.findOne.
        Optional<T> opt = getDao().findById(Long.valueOf(id));
        return opt.orElse(null);
    }

    @Override
    public List<T> findAll() {
        // FIX: Replaced Guava Lists.newArrayList(iterable) with standard Stream API
        return ((List<T>) getDao().findAll()).stream().collect(Collectors.toList());
    }

    @Override
    public void create(final T entity) {
        getDao().save(entity);
    }

    @Override
    public T update(final T entity) {
        return getDao().save(entity);
    }

    @Override
    public void delete(final T entity) {
        getDao().delete(entity);
    }

    @Override
    public void deleteById(final long entityId) {
        getDao().deleteById(Long.valueOf(entityId));
    }

    protected abstract CrudRepository<T, Serializable> getDao();
}
