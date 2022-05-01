package com.baeldung.pizza_service.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.baeldung.pizza_service.adapter.PizzaRestController;
import com.baeldung.pizza_service.core.domain.Pizza;
import com.baeldung.pizza_service.port.PizzaService;

public class PizzaRestControllerTest {

	private PizzaRestController controller = new PizzaRestController();

	private PizzaService pizzaService;

	@Before
	public void setupMock() {
		pizzaService = mock(PizzaService.class);
	}

	@Test
	public void createPizza() {
		controller.setPizzaService(pizzaService);
		Pizza p = new Pizza();
		String[] toppings = new String[] { "Onion", "Pepper", "Paneer" };
		p.setName("Mexican");
		p.setPrice(400);
		p.setToppings(toppings);
		assertNotNull(pizzaService);
		controller.createPizza(p);
	}

	@Test
	public void getPizza() {
		Pizza p = new Pizza();
		String[] toppings = new String[] { "Onion", "Pepper", "Paneer" };
		p.setName("Mexican");
		p.setPrice(400);
		p.setToppings(toppings);
		controller.setPizzaService(pizzaService);
		when(pizzaService.getPizza(Mockito.anyString())).thenReturn(p);
		assertNotNull(controller.getPizza("Mexican"));
	}

	@Test
	public void listPizza() {
		controller.setPizzaService(pizzaService);
		assertNotNull(pizzaService);
		assertNotNull(controller.listPizza());
	}

}
