package com.baeldung.domain;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private final String name;
    private final List<Ingredient> ingredients = new ArrayList<>();

    public Recipe(String name) {
        this.name = name;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        StringBuilder recipeString = new StringBuilder();
        recipeString.append("Recipe{name='");
        recipeString.append(name);
        recipeString.append("', ingredients='");
        for(Ingredient ingredient : ingredients) {
            recipeString.append(ingredient.toString());
            if (ingredients.indexOf(ingredient) != ingredients.size() - 1) {
                recipeString.append(",");
            }
        }
        recipeString.append("'}");
        return recipeString.toString();
    }
}
