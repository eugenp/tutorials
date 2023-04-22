package com.baeldung.concurrentrequest;

public class Product {

    private final int id;
    private final String name;
    private final Stock stock;

    public Product(int id, String name, Stock stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
