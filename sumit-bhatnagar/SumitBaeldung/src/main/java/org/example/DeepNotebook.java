package org.example;

import java.util.ArrayList;
import java.util.List;

public class DeepNotebook {
    public List<Recipe> recipes;

    public DeepNotebook() {
        this.recipes = new ArrayList<>();
    }

    // Deep Copy Constructor
    public DeepNotebook(DeepNotebook otherNotebook) {
        this.recipes = new ArrayList<>();
        for (Recipe recipe : otherNotebook.recipes) {
            this.recipes.add(new Recipe(recipe));  // Copying each recipe
        }
    }

    public void addRecipe(String title, String description) {
        recipes.add(new Recipe(title, description));
    }

    // Nested Recipe class with a copy constructor
    private static class Recipe {
        private String title;
        private String description;

        public Recipe(String title, String description) {
            this.title = title;
            this.description = description;
        }

        // Copy constructor for Recipe
        public Recipe(Recipe otherRecipe) {
            this.title = otherRecipe.title;
            this.description = otherRecipe.description;
        }
    }
}
