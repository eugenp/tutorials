package com.baeldung.constructordetector;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    private String value;
    private double price;

    @JsonCreator
    public Product(@JsonProperty("value") String value, @JsonProperty("price") double price) {
        this.value = value;
        this.price = price;
    }

    public String getName() {
        return value;
    }

    public double getPrice() {
        return price;
    }
}
