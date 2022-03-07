package com.baeldung.pattern.hexagonal2.domain.model;

import java.util.UUID;

public class User {
    UUID id;
    String userName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
