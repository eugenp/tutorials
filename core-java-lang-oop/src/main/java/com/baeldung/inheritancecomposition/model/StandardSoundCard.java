package com.baeldung.inheritancecomposition.model;

public class StandardSoundCard implements SoundCard {
    
    private String brand;

    public StandardSoundCard(String brand) {
        this.brand = brand;
    }
    
    @Override
    public String getBrand() {
        return brand;
    }
    
    @Override
    public String toString() {
        return "SoundCard{" + "brand=" + brand + "}";
    }  
}
