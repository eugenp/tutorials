package com.baeldung.hexagonal.architecture.service;

import com.baeldung.hexagonal.architecture.model.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaRepo pizzaRepo;

    @Override
    public void createPizza(String pizza) {
        pizzaRepo.createPizza(pizza);
    }

    @Override
    public Pizza getPizza(String name) {

        return pizzaRepo.getPizza(name);
    }

    @Override
    public List<Pizza> listPizza() {
        return pizzaRepo.getAllPizza();
    }

}