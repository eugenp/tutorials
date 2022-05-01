package com.baeldung.pizza_service.core.Impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.baeldung.pizza_service.core.domain.Pizza;
import com.baeldung.pizza_service.core.impl.PizzaServiceImpl;
import com.baeldung.pizza_service.port.PizzaRepo;

public class PizzaServiceImplTest {

	private PizzaServiceImpl serviceImpl = new PizzaServiceImpl();

	private PizzaRepo repo;

	@Before
	public void setupMock() {
		repo = mock(PizzaRepo.class);
	}

	@Test
	public void createPizzaTest() {
		serviceImpl.setPizzaRepo(repo);
		Pizza p = new Pizza();
		String[] toppings = new String[] { "Onion", "Pepper", "Paneer" };
		p.setName("Mexican");
		p.setPrice(400);
		p.setToppings(toppings);
		assertNotNull(repo);
		serviceImpl.createPizza(p);

	}

	@Test
	public void getPizzaTest() {
		Pizza p = new Pizza();
		String[] toppings = new String[] { "Onion", "Pepper", "Paneer" };
		p.setName("Mexican");
		p.setPrice(400);
		p.setToppings(toppings);
		serviceImpl.setPizzaRepo(repo);
		when(repo.getPizza(Mockito.anyString())).thenReturn(p);
		assertNotNull(repo);
		assertNotNull(serviceImpl.getPizza("Mexican"));

	}

	@Test
	public void loadPizzaTest() {
		serviceImpl.setPizzaRepo(repo);
		assertNotNull(repo);
		assertNotNull(serviceImpl.laodPizza());

	}

}
