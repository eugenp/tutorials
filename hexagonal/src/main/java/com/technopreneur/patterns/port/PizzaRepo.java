package com.technopreneur.patterns.port;

import java.util.List;

import com.technopreneur.patterns.core.domain.Pizza;

public interface PizzaRepo {
	
	void createPizza(Pizza pizza);
	
	Pizza getPizza(String name);
	
	List<Pizza> getAllPizza();

}
