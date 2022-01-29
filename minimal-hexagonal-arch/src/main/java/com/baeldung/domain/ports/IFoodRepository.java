package com.baeldung.domain.ports;

import com.baeldung.domain.Food;
import com.baeldung.domain.Ingredient;

import java.util.List;

public interface IFoodRepository {
    void store(Food food);
    void consume(Food food);
    boolean isPresent(Food food);
    boolean hasEnoughAmount(Ingredient ingredient);
    List<Food> getStoredFoods();
}
