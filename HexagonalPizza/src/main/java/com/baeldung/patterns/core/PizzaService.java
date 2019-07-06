package com.baeldung.patterns.core;

import java.util.List;

import com.baeldung.patterns.domain.Pizza;

public interface PizzaService {
	
	public void createPizza(Pizza pizza);
	
	public Pizza getPizza(String name);
	
	public List<Pizza> listPizza();

}
