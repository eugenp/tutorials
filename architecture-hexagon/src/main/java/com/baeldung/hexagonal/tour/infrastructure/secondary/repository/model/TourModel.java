package com.baeldung.hexagonal.tour.infrastructure.secondary.repository.model;

public class TourModel {
    private String source;
    private String destination;
    private String name;
    private int price;

    public TourModel(String source, String destination, String name, int price) {
        this.source = source;
        this.destination = destination;
        this.name = name;
        this.price = price;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
