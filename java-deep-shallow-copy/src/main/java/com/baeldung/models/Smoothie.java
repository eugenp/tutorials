package com.baeldung.models;

public class Smoothie {

    public Flavor flavor;
    public Ingredients ingredients;

    // Deep copy constructor
    public Smoothie(Flavor flavor, Ingredients ingredients) {
        this.flavor = flavor;
        this.ingredients = ingredients;
    }

    // standard setters and getters
    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }
}
