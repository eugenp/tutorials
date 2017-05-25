package com.baeldung.diconst.model;

public class Tyre {
    private String brand;
    private String size;
    
    public Tyre(String brand, String size) {
        this.brand = brand;
        this.size = size;
    }
    
    @Override
    public String toString() {
        return "Tyres of brand " + this.brand + " and size " + this.size;
    }
}
