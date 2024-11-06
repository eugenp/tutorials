package com.baeldung.sonarqubeandjacoco.product;

public class Product {

    private int id;
    private String name;
    private int units;
    private double price;

    public Product() {
        super();
    }

    public Product(int id, String name, int units, double price) {
        super();
        this.id = id;
        this.name = name;
        this.units = units;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}