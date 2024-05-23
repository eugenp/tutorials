package com.baeldung.quarkus.rbac.users.errors;

import java.io.Serializable;

public abstract class DomainDataException extends RuntimeException {

    private final Serializable entity;

    protected DomainDataException(final String message, final Serializable entity) {
        super(message);
        this.entity = entity;
    }

    public Serializable getEntity() {
        return entity;
    }
}
