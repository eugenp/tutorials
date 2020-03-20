package com.bealdung.hexagonalarchitecture.bootstrap;

import com.bealdung.hexagonalarchitecture.core.Food;
import com.bealdung.hexagonalarchitecture.port.FoodRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FootLoader implements CommandLineRunner {

    private FoodRepo foodRepository;

    public FootLoader(FoodRepo foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadObjects();
    }

    private void loadObjects() {
        String foodName = "burger";
        String[] ingredients = {"beef", "tomato", "lettuce", "bread"};
        Food food = new Food(foodName, ingredients);
        foodRepository.createFood(food);
    }
}
