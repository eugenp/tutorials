package com.baeldung.persistence.dao.common;

import java.io.Serializable;
import java.util.Objects; // FIX: Import standard Java utility

public abstract class AbstractDao<T extends Serializable> implements IOperations<T> {

    protected Class<T> clazz;

    protected final void setClazz(final Class<T> clazzToSet) {
        // FIX: Replaced Preconditions.checkNotNull with standard Java Objects.requireNonNull
        clazz = Objects.requireNonNull(clazzToSet);
    }
}
