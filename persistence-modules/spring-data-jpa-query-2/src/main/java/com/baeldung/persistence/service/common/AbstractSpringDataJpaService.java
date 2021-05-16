package com.baeldung.persistence.service.common;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.baeldung.persistence.dao.common.IOperations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Transactional(value = "jpaTransactionManager")
public abstract class AbstractSpringDataJpaService<T extends Serializable> implements IOperations<T> {

    @Override
    public T findOne(final long id) {
        Optional<T> opt = getDao().findById(Long.valueOf(id));
        return opt.get();
    }

    @Override
    public List<T> findAll() {
        return Lists.newArrayList(getDao().findAll());
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
