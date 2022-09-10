package com.baeldung.msf4j.msf4jspring.repositories;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.baeldung.msf4j.msf4jspring.domain.Meal;

@Component
public class MealRepository {

    private List<Meal> meals = new ArrayList<Meal>();

    public MealRepository() {
        meals.add(new Meal("Salad", 4.2f));
        meals.add(new Meal("Litre of cola", 2.99f));
    }

    public void create(Meal meal) {
        meals.add(meal);
    }

    public void remove(Meal meal) {
        meals.remove(meal);
    }

    public Meal find(int id) {
        return meals.get(id);
    }

    public List<Meal> findAll() {
        return meals;
    }

    public void update(int id, Meal meal) {
        meals.set(id, meal);
    }
}