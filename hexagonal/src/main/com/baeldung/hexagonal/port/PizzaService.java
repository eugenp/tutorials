package com.baeldung.hexagonal.port;

import java.util.List;

import com.baeldung.hexagonal.core.domain.Pizza;

public interface PizzaService {
	
	public void createPizza(Pizza pizza);
	
	public Pizza getPizza(String name);
	
	public List<Pizza> listPizza();

}
