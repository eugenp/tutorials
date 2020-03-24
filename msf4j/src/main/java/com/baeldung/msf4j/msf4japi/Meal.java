package com.baeldung.msf4j.msf4japi;

public class Meal {
    private String name;
    private Float price;

    public Meal(String name, Float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

}
