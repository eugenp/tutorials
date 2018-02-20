package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PizzaMaker {

    private final PizzaOven pizzaOven;

    private final PizzaAssembler pizzaAssembler;

    @Autowired
    public PizzaMaker(PizzaOven pizzaOven, PizzaAssembler pizzaAssembler) {
        this.pizzaOven = pizzaOven;
        this.pizzaAssembler = pizzaAssembler;
    }
}
