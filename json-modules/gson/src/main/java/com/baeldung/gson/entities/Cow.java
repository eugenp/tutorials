package com.baeldung.gson.entities;

public class Cow extends Animal {
    private String breed;

    public Cow() {
        breed = "Jersey";
        type = "Cow";
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}

