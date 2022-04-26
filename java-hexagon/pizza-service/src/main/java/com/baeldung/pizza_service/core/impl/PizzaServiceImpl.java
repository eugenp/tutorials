package com.baeldung.pizza_service.core.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.pizza_service.core.domain.Pizza;
import com.baeldung.pizza_service.port.PizzaRepo;
import com.baeldung.pizza_service.port.PizzaService;

@Service
public class PizzaServiceImpl implements PizzaService {

	@Autowired
	private PizzaRepo pizzaRepo;
	
	@Override
	public void createPizza(Pizza pizza) {
		// TODO Auto-generated method stub
		pizzaRepo.createPizza(pizza);
	}

	@Override
	public Pizza getPizza(String name) {
		// TODO Auto-generated method stub
		return pizzaRepo.getPizza(name);
	}

	@Override
	public List<Pizza> laodPizza() {
		// TODO Auto-generated method stub
		return pizzaRepo.getAllPizza();
	}

}
