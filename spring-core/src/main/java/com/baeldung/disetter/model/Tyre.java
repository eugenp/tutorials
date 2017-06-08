package com.baeldung.disetter.model;

public class Tyre {
    private String brand;
    private String size;
    
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Tyres of brand " + this.brand + " and size " + this.size;
    }
}
