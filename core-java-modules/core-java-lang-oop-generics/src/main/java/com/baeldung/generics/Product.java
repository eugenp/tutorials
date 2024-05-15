package com.baeldung.generics;

import java.io.Serializable;

public class Product implements Rankable, Serializable {
    private String name;
    private double price;
    private int sales;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public int getRank() {
        return sales;
    }

    @Override
    public String toString() {
        return "Product [name=" + name + ", price=" + price + ", sales=" + sales + "]";
    }

    // getters and setters
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

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

}
