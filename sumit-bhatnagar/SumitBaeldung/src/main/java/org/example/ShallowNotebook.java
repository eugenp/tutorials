package org.example;

import java.util.ArrayList;
import java.util.List;

public class ShallowNotebook {
    public List<Recipe> recipes;

    // Constructor
    public ShallowNotebook() {
        this.recipes = new ArrayList<>();
    }

    // Shallow Copy Constructor
    public ShallowNotebook(ShallowNotebook otherShallowNotebook) {
        this.recipes = otherShallowNotebook.recipes;  // Only the reference is copied
    }

    // Add a recipe to the notebook
    public void addRecipe(String title, String description) {
        recipes.add(new Recipe(title, description));
    }

    // Nested Recipe class
    private static class Recipe {
        private String title;
        private String description;

        public Recipe(String title, String description) {
            this.title = title;
            this.description = description;
        }
    }
}
