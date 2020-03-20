package com.bealdung.hexagonalarchitecture.core;

import com.bealdung.hexagonalarchitecture.port.FoodRepo;
import com.bealdung.hexagonalarchitecture.port.FoodService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    FoodRepo foodRepo;

    public FoodServiceImpl(FoodRepo foodRepo) {
        this.foodRepo = foodRepo;
    }

    @Override
    public void createFood(Food food) {
        foodRepo.createFood(food);
    }

    @Override
    public Food getFood(String name) {
        return foodRepo.getFood(name);
    }

    @Override
    public List<Food> getAllFood() {
        return foodRepo.getAllFood();
    }
}
