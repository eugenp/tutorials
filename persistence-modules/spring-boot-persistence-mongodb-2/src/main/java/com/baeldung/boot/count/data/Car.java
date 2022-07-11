package com.baeldung.boot.count.data;

import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class Car {
    private String name;

    private String brand;

    public Car() {
    }

    public Car(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
