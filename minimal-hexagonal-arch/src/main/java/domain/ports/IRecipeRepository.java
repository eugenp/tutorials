package domain.ports;

import domain.Recipe;

import java.util.List;

public interface IRecipeRepository {
    List<Recipe> getRecipes();
}
