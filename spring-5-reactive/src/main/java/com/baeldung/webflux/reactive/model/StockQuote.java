package com.baeldung.webflux.reactive.model;

public class StockQuote {
    private String name;
    private double price;

    public StockQuote(String name, double price) {
        this.name = name;
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

    @Override
    public String toString() {
        return "StockQuote{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
