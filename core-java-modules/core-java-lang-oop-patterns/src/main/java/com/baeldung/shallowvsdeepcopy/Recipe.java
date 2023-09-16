package com.baeldung.shallowvsdeepcopy;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String name;
    private List<String> ingredients;

    public Recipe(String name) {
        this.name = name;
        this.ingredients = new ArrayList<String>();
    }

    public Recipe(String name, List<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public static Recipe shallow(Recipe recipe) {
        return new Recipe(recipe.name, recipe.ingredients);
    }

    public static Recipe deep(Recipe recipe) {
        return new Recipe(recipe.name, new ArrayList<String>(recipe.ingredients));
    }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
