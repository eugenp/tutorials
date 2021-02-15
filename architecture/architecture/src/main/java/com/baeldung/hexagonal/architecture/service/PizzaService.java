package com.baeldung.hexagonal.architecture.service;

import com.baeldung.hexagonal.architecture.model.Pizza;

import java.util.List;

public interface PizzaService {
    public void createPizza(String name);

    public Pizza getPizza(String name);

    public List<Pizza> listPizza();
}