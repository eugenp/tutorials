package com.baeldung.mockito.fluentapi;

import java.util.List;
import java.util.Optional;

import com.baeldung.mockito.fluentapi.Pizza.PizzaSize;

public class PizzaService {

    private Pizza pizza;

    public PizzaService(Pizza.PizzaBuilder builder) {
        this.pizza = builder.build();
    }

    public List<String> listToppings(Pizza.PizzaBuilder builder) {
        Pizza build = builder.size(PizzaSize.LARGE)
            .withExtaTopping("Mushroom")
            .withStuffedCrust(false)
            .willCollect(true)
            .applyDiscount(20)
            .build();
    }

    public boolean isLarge() {
        return PizzaSize.LARGE.equals(pizza.getSize());
    }

}
