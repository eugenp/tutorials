package com.baeldung.persistence.service.common;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.persistence.IOperations;

@Transactional
public abstract class AbstractService<T extends Serializable> implements IOperations<T> {

    // read - all

    @Override
    public Page<T> findPaginated(final int page, final int size) {
        return getDao().findAll(PageRequest.of(page, size));
    }

    // write

    @Override
    public T create(final T entity) {
        return getDao().save(entity);
    }

    protected abstract PagingAndSortingRepository<T, Long> getDao();

}
