package com.baeldung.designpatterns.bridge;

public class Blue implements Color {
    @Override
    public String fill() {
        return "Color is Blue";
    }
}
