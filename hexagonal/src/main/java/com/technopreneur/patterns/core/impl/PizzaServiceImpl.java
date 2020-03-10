package com.technopreneur.patterns.core.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technopreneur.patterns.core.domain.Pizza;
import com.technopreneur.patterns.port.PizzaRepo;
import com.technopreneur.patterns.port.PizzaService;

@Service
public class PizzaServiceImpl implements PizzaService {

	@Autowired
	private PizzaRepo pizzaRepo;

	@Override
	public void createPizza(Pizza pizza) {
		pizzaRepo.createPizza(pizza);
	}

	@Override
	public Pizza getPizza(String name) {

		return pizzaRepo.getPizza(name);
	}

	@Override
	public List<Pizza> listPizza() {
		return pizzaRepo.getAllPizza();
	}

}
