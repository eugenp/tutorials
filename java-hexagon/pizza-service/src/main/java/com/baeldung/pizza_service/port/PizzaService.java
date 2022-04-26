package com.baeldung.pizza_service.port;

import java.util.List;

import com.baeldung.pizza_service.core.domain.Pizza;

public interface PizzaService {
	public void createPizza(Pizza pizza);
	public Pizza getPizza(String name);
	public List<Pizza> laodPizza();
}
