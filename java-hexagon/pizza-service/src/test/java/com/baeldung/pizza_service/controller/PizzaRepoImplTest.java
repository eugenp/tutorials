package com.baeldung.pizza_service.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.baeldung.pizza_service.adapter.PizzaRepoImpl;
import com.baeldung.pizza_service.core.domain.Pizza;

public class PizzaRepoImplTest {

	private PizzaRepoImpl repoImpl = new PizzaRepoImpl();

	@Test
	public void createPizzaTest() {
		Pizza p = new Pizza();
		String[] toppings = new String[] { "Onion", "Pepper", "Paneer" };
		p.setName("Mexican");
		p.setPrice(400);
		p.setToppings(toppings);
		repoImpl.createPizza(p);
	}

	@Test
	public void getAllPizzaTest() {
		assertNotNull(repoImpl.getAllPizza());
	}
}
