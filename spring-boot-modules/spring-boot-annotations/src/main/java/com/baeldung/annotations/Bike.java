package com.baeldung.annotations;

import org.springframework.context.annotation.DependsOn;

@DependsOn
public class Bike implements Vehicle {

    private String color;

    public Bike(String color) {
        this.color = color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
