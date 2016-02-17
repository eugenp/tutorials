package com.baeldung.persistence.dao.common;

import java.io.Serializable;

import com.google.common.base.Preconditions;

public abstract class AbstractDao<T extends Serializable> implements IOperations<T> {

    protected Class<T> clazz;

    protected final void setClazz(final Class<T> clazzToSet) {
        clazz = Preconditions.checkNotNull(clazzToSet);
    }
}
