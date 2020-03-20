package com.bealdung.hexagonalarchitecture.core;

public class Food {

    private String name;

    private String[] ingredients;

    public Food(String name, String[] ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
