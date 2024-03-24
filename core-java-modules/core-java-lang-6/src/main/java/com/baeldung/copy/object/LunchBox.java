package com.baeldung.copy.object;

import java.util.ArrayList;
import java.util.List;

public class LunchBox {

    private List<Food> foodList = new ArrayList<>();

    public void addFood(Food food) {
        foodList.add(food);
    }

    public Food grabFood() {
        return foodList.stream()
            .findAny()
            .orElse(null);
    }

    public void replaceFood(Food food) {
        foodList.clear();
        addFood(food);
    }

    public List<Food> getFoodList() {
        return foodList;
    }
}
