package com.baeldung.hexagon.tour.suggest.dto;

public class Tour {
    private String name;
    private int price;

    public Tour(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
