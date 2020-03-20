package com.bealdung.hexagonalarchitecture.port;

import com.bealdung.hexagonalarchitecture.core.Food;

import java.util.List;

public interface FoodRepo {

    void createFood(Food food);

    Food getFood(String name);

    List<Food> getAllFood();
}
