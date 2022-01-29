package domain.ports;

import domain.Food;
import domain.Ingredient;

import java.util.List;

public interface IFoodRepository {
    void store(Food food);
    void consume(Food food);
    boolean isPresent(Food food);
    boolean hasEnoughAmount(Ingredient ingredient);
    List<Food> getStoredFoods();
}
