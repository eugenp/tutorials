package com.baeldung.quarkus.rbac.users.errors;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String id) {
        super("Entity with id " + id + " not found");
    }
}
