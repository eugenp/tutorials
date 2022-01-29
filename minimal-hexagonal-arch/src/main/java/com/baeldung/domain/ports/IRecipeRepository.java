package com.baeldung.domain.ports;

import com.baeldung.domain.Recipe;

import java.util.List;

public interface IRecipeRepository {
    List<Recipe> getRecipes();
}
