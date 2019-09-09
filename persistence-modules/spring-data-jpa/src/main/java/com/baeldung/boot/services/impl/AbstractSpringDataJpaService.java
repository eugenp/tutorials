package com.baeldung.boot.services.impl;

import com.baeldung.boot.services.IOperations;
import com.google.common.collect.Lists;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional(value = "transactionManager")
public abstract class AbstractSpringDataJpaService<T extends Serializable> implements IOperations<T> {

    @Override
    public T findOne(final long id) {
        return getDao().findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

    @Override
    public T create(final T entity) {
        return getDao().save(entity);
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
        getDao().deleteById(entityId);
    }

    protected abstract CrudRepository<T, Serializable> getDao();
}
