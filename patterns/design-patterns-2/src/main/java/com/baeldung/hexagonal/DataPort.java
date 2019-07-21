package com.baeldung.hexagonal;

public interface DataPort {
    
    CoffeeRecipe getCoffeeRecipeById(Integer id);
}
