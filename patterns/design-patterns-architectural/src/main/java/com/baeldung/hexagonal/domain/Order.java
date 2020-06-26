package com.baeldung.hexagonal.domain;

public class Order {
    String id;

    public Order(String sku) {
        id = sku;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}