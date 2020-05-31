package com.baeldung.hexagonalsmallexp.domain;

import java.util.UUID;

public class Product {
    private UUID id;

    private int price;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Product() {
    }

    public Product(UUID id, int price) {
        this.id = id;
        this.price = price;
    }
}
