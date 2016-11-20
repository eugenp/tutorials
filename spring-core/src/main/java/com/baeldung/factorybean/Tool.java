package com.baeldung.factorybean;

public class Tool {
    private int id;// standard setters and getters
    private String name;// standard setters and getters
    private double price;// standard setters and getters

    public Tool() {
    }

    public Tool(int id, String name, double price) {
        this.id = id;
        this.name = name;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
