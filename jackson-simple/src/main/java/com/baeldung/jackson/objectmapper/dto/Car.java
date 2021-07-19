package com.baeldung.jackson.objectmapper.dto;

public class Car {

    private String color;
    private String type;

    public Car() {
    }

    public Car(final String color, final String type) {
        this.color = color;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
