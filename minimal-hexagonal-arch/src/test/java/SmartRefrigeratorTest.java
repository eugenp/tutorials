import domain.Food;
import domain.SmartRefrigerator;
import domain.Unit;
import domain.ports.IFoodRepository;
import domain.ports.IRecipeRepository;
import domain.ports.ISmartRefrigerator;
import infra.MockFoodRepository;
import infra.MockRecipeRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class SmartRefrigeratorTest {
    @Test
    public void Should_Give_Nothing_When_Asked_What_I_Have() {
        IFoodRepository foodStorage = new MockFoodRepository();
        IRecipeRepository recipeStorage = new MockRecipeRepository();
        ISmartRefrigerator smartRefrigerator = new SmartRefrigerator(foodStorage, recipeStorage);
        assertEquals("", smartRefrigerator.whatIHave());
    }

    @Test
    public void Should_Give_10_Piece_Egg_When_Asked_What_I_Have() {
        Food food = new Food("Egg", 10, Unit.Piece);
        IFoodRepository foodStorage = new MockFoodRepository();
        foodStorage.store(food);
        IRecipeRepository recipeStorage = new MockRecipeRepository();
        ISmartRefrigerator smartRefrigerator = new SmartRefrigerator(foodStorage, recipeStorage);
        assertEquals("10 Piece Egg", smartRefrigerator.whatIHave());
    }

    @Test
    public void Should_Give_Updated_Amount_Of_Food_When_Existing_Food_Is_Added() {
        Food initialEggs = new Food("Egg", 10, Unit.Piece);
        IFoodRepository foodStorage = new MockFoodRepository();
        foodStorage.store(initialEggs);
        Food eggs = new Food("Egg", 10, Unit.Piece);
        foodStorage.store(eggs);
        IRecipeRepository recipeStorage = new MockRecipeRepository();
        ISmartRefrigerator smartRefrigerator = new SmartRefrigerator(foodStorage, recipeStorage);
        assertEquals("20 Piece Egg", smartRefrigerator.whatIHave());
    }

    @Test
    public void Should_Give_Updated_Amount_Of_Food_When_Existing_Food_Is_Consumed() {
        Food initialEggs = new Food("Egg", 10, Unit.Piece);
        IFoodRepository foodStorage = new MockFoodRepository();
        foodStorage.store(initialEggs);
        Food consumedEggs = new Food("Egg", 5, Unit.Piece);
        foodStorage.consume(consumedEggs);
        IRecipeRepository recipeStorage = new MockRecipeRepository();
        ISmartRefrigerator smartRefrigerator = new SmartRefrigerator(foodStorage, recipeStorage);
        assertEquals("5 Piece Egg", smartRefrigerator.whatIHave());
    }

    @Test
    public void Should_Remove_Finished_Food_When_All_Amount_Is_Consumed() {
        Food initialEggs = new Food("Egg", 10, Unit.Piece);
        Food butter = new Food("Butter", 500, Unit.Grams);
        IFoodRepository foodStorage = new MockFoodRepository();
        foodStorage.store(initialEggs);
        foodStorage.store(butter);
        Food consumedEggs = new Food("Egg", 10, Unit.Piece);
        foodStorage.consume(consumedEggs);
        IRecipeRepository recipeStorage = new MockRecipeRepository();
        ISmartRefrigerator smartRefrigerator = new SmartRefrigerator(foodStorage, recipeStorage);
        assertEquals("500 Grams Butter", smartRefrigerator.whatIHave());
    }

    @Test
    public void Should_Give_Multiple_Foods_When_Asked_What_I_Have() {
        Food initialEggs = new Food("Egg", 10, Unit.Piece);
        Food butter = new Food("Butter", 500, Unit.Grams);
        IFoodRepository foodStorage = new MockFoodRepository();
        foodStorage.store(initialEggs);
        foodStorage.store(butter);
        IRecipeRepository recipeStorage = new MockRecipeRepository();
        ISmartRefrigerator smartRefrigerator = new SmartRefrigerator(foodStorage, recipeStorage);
        assertEquals("10 Piece Egg\n500 Grams Butter", smartRefrigerator.whatIHave());
    }
}
