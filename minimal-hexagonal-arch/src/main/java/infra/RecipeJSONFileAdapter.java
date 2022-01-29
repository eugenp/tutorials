package infra;

import domain.Ingredient;
import domain.Recipe;
import domain.Unit;
import domain.ports.IRecipeRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RecipeJSONFileAdapter implements IRecipeRepository {
    @Override
    public List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        InputStream is = RecipeJSONFileAdapter.class.getResourceAsStream("/recipes.json");
        if (is == null) {
            throw new NullPointerException("Cannot find resource file recipes.json");
        }
        JSONTokener tokener = new JSONTokener(is);
        JSONArray recipeList = new JSONArray(tokener);
        for(int i = 0; i < recipeList.length(); ++i) {
            JSONObject recipeJSON = (JSONObject) recipeList.get(i);
            String name = (String) recipeJSON.get("name");
            Recipe recipe = new Recipe(name);
            JSONArray ingredientList = (JSONArray) recipeJSON.get("ingredients");
            for (int j = 0; j < ingredientList.length(); j++) {
                JSONObject ingredientJSON = (JSONObject) ingredientList.get(j);
                Ingredient ingredient = new Ingredient((String) ingredientJSON.get("name"),
                        Integer.parseInt((String) ingredientJSON.get("amount")),
                        Unit.valueOf((String) ingredientJSON.get("unit")));
                recipe.addIngredient(ingredient);
            }
            recipes.add(recipe);
        }
        return recipes;
    }
}

