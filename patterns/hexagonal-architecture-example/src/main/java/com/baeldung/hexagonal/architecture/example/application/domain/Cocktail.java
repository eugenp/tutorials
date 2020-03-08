package com.baeldung.hexagonal.architecture.example.application.domain;

public class Cocktail {
    
    private String name;
    
    private double price;

    public Cocktail(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
