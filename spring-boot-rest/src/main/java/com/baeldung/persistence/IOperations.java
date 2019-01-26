package com.baeldung.persistence;

import java.io.Serializable;

import org.springframework.data.domain.Page;

public interface IOperations<T extends Serializable> {

    // read - all

    Page<T> findPaginated(int page, int size);

    // write

    T create(final T entity);
}
