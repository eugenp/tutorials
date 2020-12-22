package com.baeldung.boot.jackson.model;

import java.time.LocalDateTime;

public class Coffee {

    private String name;

    private String brand;

    private LocalDateTime date;

    public String getName() {
        return name;
    }

    public Coffee setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public Coffee setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Coffee setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }
}
