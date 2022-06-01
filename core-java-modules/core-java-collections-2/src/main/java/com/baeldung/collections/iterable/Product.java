package com.baeldung.collections.iterable;

class Product {

    private String name;
    private double price;

    public Product(String code, double price) {
        this.name = code;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
