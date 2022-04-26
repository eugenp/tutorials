package com.baeldung.pizza_service.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.pizza_service.core.domain.Pizza;
import com.baeldung.pizza_service.port.PizzaRepo;

@Repository
public class PizzaRepoImpl implements PizzaRepo {

	private Map<String, Pizza> pizzaStore = new HashMap<String, Pizza>();
	@Override
	public void createPizza(Pizza pizza) {
		// TODO Auto-generated method stub
		pizzaStore.put(pizza.getName(), pizza);
	}

	@Override
	public Pizza getPizza(String name) {
		// TODO Auto-generated method stub
		return pizzaStore.get(name);
	}

	@Override
	public List<Pizza> getAllPizza() {
		// TODO Auto-generated method stub
		return pizzaStore.values().stream().collect(Collectors.toList());
	}

}
