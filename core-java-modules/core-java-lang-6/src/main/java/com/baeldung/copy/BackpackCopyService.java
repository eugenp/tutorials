package com.baeldung.copy;

import com.baeldung.copy.object.Backpack;
import com.baeldung.copy.object.LunchBox;
import com.baeldung.copy.object.Food;

public class BackpackCopyService {

    public static Backpack shallowCopy(Backpack backpackSource) {
        Backpack backpackCopy = new Backpack(backpackSource.getSize(), backpackSource.getLunchBox());
        return backpackCopy;
    }

    public static Backpack deepCopy(Backpack backpackSource) {
        LunchBox lunchBoxCopy = new LunchBox();
        Backpack backpackCopy = new Backpack(backpackSource.getSize(), lunchBoxCopy);
        for (Food food : backpackSource.getLunchBox().getFoodList()) {
            Food newFood = new Food(food.getTaste());
            lunchBoxCopy.addFood(newFood);
        }
        return backpackCopy;
    }

}

