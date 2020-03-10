package com.baeldung.hexagonal.port;

import java.util.List;

import com.baeldung.hexagonal.core.domain.Pizza;

public interface PizzaRepo {
	
	void createPizza(Pizza pizza);
	
	Pizza getPizza(String name);
	
	List<Pizza> getAllPizza();

}
