package com.baeldung.hexagonalarchitecture.domain;

public class Flight {
    private String airline;
    private double price;

    public Flight(String airline, double price) {
        this.airline = airline;
        this.price = price;
    }

    public String getAirline() {
        return airline;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
}
