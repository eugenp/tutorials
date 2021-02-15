package com.baeldung.hexagonal.architecture.service;

import com.baeldung.hexagonal.architecture.model.Pizza;

import java.util.List;

public interface PizzaRepo {
    public void createPizza(String pizza);

    public Pizza getPizza(String name);

    public List<Pizza> getAllPizza();
}