package org.baeldung.customannotation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GenericDAO<E> {

    private Class<E> entityClass;
    private String message;

    public GenericDAO(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public List<E> findAll() {
        message = "Would create findAll query from " + entityClass.getSimpleName();
        return Collections.emptyList();
    }

    public Optional<E> persist(E toPersist) {
        message = "Would create persist query from " + toPersist.getClass().getSimpleName();
        return Optional.empty();
    }

    /** Only used for unit-testing. */
    public final String getMessage() {
        return message;
    }
}
