package com.technopreneur.patterns.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.technopreneur.patterns.core.domain.Pizza;
import com.technopreneur.patterns.port.PizzaRepo;

@Repository
public class PizzaRepoImpl implements PizzaRepo {

	private Map<String, Pizza> pizzaStore = new HashMap<String, Pizza>();

	@Override
	public void createPizza(Pizza pizza) {
		pizzaStore.put(pizza.getName(), pizza);
	}

	@Override
	public Pizza getPizza(String name) {
		return pizzaStore.get(name);
	}

	@Override
	public List<Pizza> getAllPizza() {
		return pizzaStore.values().stream().collect(Collectors.toList());
	}

}
