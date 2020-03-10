package com.technopreneur.patterns.port;

import java.util.List;

import com.technopreneur.patterns.core.domain.Pizza;

public interface PizzaService {
	
	public void createPizza(Pizza pizza);
	
	public Pizza getPizza(String name);
	
	public List<Pizza> listPizza();

}
