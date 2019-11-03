package org.baeldung.persistence.service.common;

import java.io.Serializable;
import java.util.List;

import org.baeldung.persistence.IOperations;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Transactional
public abstract class AbstractService<T extends Serializable> implements IOperations<T> {

    // read - one

    @Override
    @Transactional(readOnly = true)
    public T findOne(final long id) {
        return getDao().findById(id).orElse(null);
    }

    // read - all

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

    // write

    @Override
    public T create(final T entity) {
        return getDao().save(entity);
    }

    @Override
    public T update(final T entity) {
        return getDao().save(entity);
    }

    protected abstract PagingAndSortingRepository<T, Long> getDao();

}
