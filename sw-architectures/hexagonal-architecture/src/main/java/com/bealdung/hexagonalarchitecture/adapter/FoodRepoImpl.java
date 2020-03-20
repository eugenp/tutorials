package com.bealdung.hexagonalarchitecture.adapter;

import com.bealdung.hexagonalarchitecture.core.Food;
import com.bealdung.hexagonalarchitecture.port.FoodRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FoodRepoImpl implements FoodRepo {

    private Map<String, Food> foodStore = new HashMap<>();

    @Override
    public void createFood(Food food) {
        foodStore.put(food.getName(), food);
    }

    @Override
    public Food getFood(String name) {
        return foodStore.get(name);
    }

    @Override
    public List<Food> getAllFood() {
        return new ArrayList<>(foodStore.values());
    }
}
