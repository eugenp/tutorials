package infra;

import domain.Ingredient;
import domain.Recipe;
import domain.Unit;
import domain.ports.IRecipeRepository;

import java.util.ArrayList;
import java.util.List;

public class MockRecipeRepository implements IRecipeRepository {
    private final List<Recipe> recipes = new ArrayList<>();

    public MockRecipeRepository() {
        Recipe recipe = new Recipe("Omelette");
        Ingredient eggs = new Ingredient("Egg", 2, Unit.Piece);
        Ingredient butter = new Ingredient("Butter", 50, Unit.Grams);
        recipe.addIngredient(eggs);
        recipe.addIngredient(butter);
        recipes.add(recipe);
    }


    @Override
    public List<Recipe> getRecipes() {
        return recipes;
    }
}
