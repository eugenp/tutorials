package com.baeldung.boot.jackson.model;

public class Coffee {

    private String name;

    private String brand;

    public String getName() {
        return name;
    }

    public Coffee setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public Coffee setBrand(String brand) {
        this.brand = brand;
        return this;
    }
}
