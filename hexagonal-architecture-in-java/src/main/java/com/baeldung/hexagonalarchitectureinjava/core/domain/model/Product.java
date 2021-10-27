package com.baeldung.hexagonalarchitectureinjava.core.domain;

import java.util.UUID;

public class Product {

    private UUID id;
    private String name;
    private String category;
    private double price;

    public Product(UUID id, String name, String category, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }
}
