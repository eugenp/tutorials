package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.baeldung.domain.Food;
import com.baeldung.domain.SmartRefrigerator;
import com.baeldung.domain.Unit;
import com.baeldung.domain.ports.IFoodRepository;
import com.baeldung.domain.ports.IRecipeRepository;
import com.baeldung.domain.ports.ISmartRefrigerator;
import com.baeldung.infra.MockFoodRepository;
import com.baeldung.infra.MockRecipeRepository;

public class SmartRefrigeratorUnitTest {
    @Test
    public void givenMockFoodStorageAndMockRecipeStorage_whenAskedWhatIHave_thenEmptyStringIsReturned() {
        IFoodRepository foodStorage = new MockFoodRepository();
        IRecipeRepository recipeStorage = new MockRecipeRepository();
        ISmartRefrigerator smartRefrigerator = new SmartRefrigerator(foodStorage, recipeStorage);
        assertEquals("", smartRefrigerator.whatIHave());
    }

    @Test
    public void givenSomeEggs_whenAskedWhatIHave_thenCorrectContentIsReturned() {
        Food food = new Food("Egg", 10, Unit.Piece);
        IFoodRepository foodStorage = new MockFoodRepository();
        foodStorage.store(food);
        IRecipeRepository recipeStorage = new MockRecipeRepository();
        ISmartRefrigerator smartRefrigerator = new SmartRefrigerator(foodStorage, recipeStorage);
        assertEquals("10 Piece Egg", smartRefrigerator.whatIHave());
    }

    @Test
    public void givenSomeEggs_whenMoreEggsAreAddedAndAskedWhatIHave_thenCorrectContentIsReturned() {
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
    public void givenSomeEggs_whenSomeEggsAreConsumedAndAskedWhatIHave_thenCorrectContentIsReturned() {
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
    public void givenSomeEggsAndSomeButter_whenAllEggsAreConsumedAndAskedWhatIHave_thenOnlyButterContentIsReturned() {
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
    public void givenSomeEggsAndSomeButter_whenAskedWhatIHave_thenCorrectContentIsReturned() {
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
