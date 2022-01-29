package domain;

import domain.ports.IFoodRepository;
import domain.ports.IRecipeRepository;
import domain.ports.ISmartRefrigerator;

import java.util.List;
import java.util.Optional;

public class SmartRefrigerator implements ISmartRefrigerator {
    private final IFoodRepository foodRepository;
    private final IRecipeRepository recipeRepository;

    public SmartRefrigerator(IFoodRepository foodRepository, IRecipeRepository recipeRepository) {
        this.foodRepository = foodRepository;
        this.recipeRepository = recipeRepository;
    }

    public void storeFood(Food food) {
        foodRepository.store(food);;
    }

    public void consumeFood(Food food) {
        foodRepository.consume(food);
    }

    public String whatIHave() {
        List<Food> storedFoods = foodRepository.getStoredFoods();
        StringBuilder whatIHaveString = new StringBuilder();
        for(Food food : storedFoods) {
            whatIHaveString.append(food.toString());
            if (storedFoods.indexOf(food) != storedFoods.size() - 1) {
                whatIHaveString.append('\n');
            }
        }
        return whatIHaveString.toString();
    }

    public Optional<Recipe> offerMeARecipe() {
        List<Recipe> recipes = recipeRepository.getRecipes();
        for(Recipe recipe : recipes) {
            List<Ingredient> ingredients = recipe.getIngredients();
            boolean cookingPossible = true;
            for(Ingredient ingredient : ingredients) {
                cookingPossible |= foodRepository.hasEnoughAmount(ingredient);
            }
            if (cookingPossible) {
                return Optional.of(recipe);
            }
        }
        return Optional.empty();
    }
}
