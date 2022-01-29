package com.baeldung.infra;

import com.baeldung.domain.Food;
import com.baeldung.domain.Ingredient;
import com.baeldung.domain.ports.IFoodRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockFoodRepository implements IFoodRepository {
    private List<Food> foods = new ArrayList<>();

    @Override
    public void store(Food food) {
        if (isPresent(food)) {
            Food foundFood = foods.stream().filter(storedFood -> storedFood.getName().equalsIgnoreCase(food.getName()))
                    .findFirst().get();
            foundFood.setAmount(foundFood.getAmount() + food.getAmount());
        } else {
            foods.add(food);
        }
    }

    @Override
    public void consume(Food food) {
        Optional<Food> optionalFood = foods.stream()
                .filter(storedFood -> food.getName().equals(storedFood.getName())).findFirst();
        if (optionalFood.isPresent()) {
            Food foundFood = optionalFood.get();
            if (foundFood.getAmount() >= food.getAmount()) {
                foundFood.setAmount(foundFood.getAmount() - food.getAmount());
                if (foundFood.getAmount() == 0) {
                    foods.remove(foundFood);
                }
            }
        } else {
            // TODO: Make better
            System.out.println("Food is not present");
        }
    }

    @Override
    public boolean isPresent(Food food) {
        return foods.contains(food);
    }

    @Override
    public boolean hasEnoughAmount(Ingredient ingredient) {
        Food food = new Food(ingredient.getName(), ingredient.getAmount(), ingredient.getUnit());
        if (isPresent(food)) {
            Food foundFood = foods.stream().filter(storedFood -> storedFood.getName().equalsIgnoreCase(food.getName()))
                    .findFirst().get();
            if (foundFood.getAmount() >= food.getAmount()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<Food> getStoredFoods() {
        return foods;
    }
}
