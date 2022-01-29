package console;

import domain.Food;
import domain.SmartRefrigerator;
import domain.Unit;
import domain.ports.IFoodRepository;
import domain.ports.IRecipeRepository;
import domain.ports.ISmartRefrigerator;
import infra.ConsoleAdapter;
import infra.MockFoodRepository;
import infra.MockRecipeRepository;
import infra.RecipeJSONFileAdapter;

public class MinimalHexagonalArchitectureDemo {
    public static void main(String ...args) {
        // Instantiate the server-side
        IFoodRepository foodRepository = new MockFoodRepository();
        Food eggs = new Food("Egg", 15, Unit.Piece);
        Food butter = new Food("Butter", 500, Unit.Grams);
        foodRepository.store(eggs);
        foodRepository.store(butter);

        // Initialize recipe repository
        IRecipeRepository recipeRepository = new RecipeJSONFileAdapter();

        // Instantiate the domain
        ISmartRefrigerator smartRefrigerator = new SmartRefrigerator(foodRepository, recipeRepository);

        // Instantiate the client-side
        ConsoleAdapter consoleAdapter = new ConsoleAdapter(smartRefrigerator);

        System.out.println("My super smart refrigerator! What do you have?");
        consoleAdapter.AskWhatRefrigeratorHas();
        System.out.println("My super smart refrigerator! What can I cook?");
        consoleAdapter.AskWhichRecipeRefrigeratorOffers();

    }
}
