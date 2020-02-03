package com.baeldung.hexagonal.domain;

import java.util.UUID;

public class Product implements IEntity {

    private UUID id;
    private String name;

    // getters setters


    public Product(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
