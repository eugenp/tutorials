package com.baeldung.hexagonal.core;

public class Product {

    private final Long id;

    private final String name;

    private final int unitPrice;

    private final int stockQuantity;

    public Product(Long id, String name, int unitPrice, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
}
