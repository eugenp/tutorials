package com.baeldung.patterns.port;

import java.util.List;

import com.baeldung.patterns.domain.Pizza;

public interface PizzaRepo {
	
	void createPizza(Pizza pizza);
	
	Pizza getPizza(String name);
	
	List<Pizza> getAllPizza();

}
