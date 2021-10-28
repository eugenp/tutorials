package com.baeldung.hexagonalarchitectureinjava.infrastructure.repository.entity;

import java.util.UUID;

public class ProductEntity {

    private final UUID id;
    private final String name;
    private final String category;
    private final double price;

    public ProductEntity(UUID id, String name, String category, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }
}
