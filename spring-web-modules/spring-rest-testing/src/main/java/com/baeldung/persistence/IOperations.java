package com.baeldung.persistence;

import java.io.Serializable;
import java.util.List;

public interface IOperations<T extends Serializable> {

    // read - one

    T findOne(final long id);

    // read - all

    List<T> findAll();

    // write

    T create(final T entity);
    
    T update(final T entity);

}
