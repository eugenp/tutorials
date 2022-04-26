package com.baeldung.pizza_service.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.pizza_service.core.domain.Pizza;
import com.baeldung.pizza_service.port.PizzaService;
import com.baeldung.pizza_service.web.PizzaRestUI;

@RestController
@RequestMapping(value = "/pizza")
public class PizzaRestController implements PizzaRestUI {

	@Autowired
	private PizzaService pizzaService;

	@Override
	public void createPizza(@RequestBody Pizza pizza) {
		// TODO Auto-generated method stub
		pizzaService.createPizza(pizza);
	}

	@Override
	public Pizza getPizza(@PathVariable String name) {
		// TODO Auto-generated method stub
		return pizzaService.getPizza(name);
	}

	@Override
	public List<Pizza> listPizza() {
		// TODO Auto-generated method stub
		return pizzaService.laodPizza();
	}

}
