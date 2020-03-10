package com.technopreneur.patterns.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technopreneur.patterns.core.domain.Pizza;
import com.technopreneur.patterns.port.PizzaService;
import com.technopreneur.patterns.web.PizzaRestUI;

@RestController
@RequestMapping("/pizza")
public class PizzaRestController implements PizzaRestUI {

	@Autowired
	private PizzaService pizzaService;

	@Override
	public void createPizza(@RequestBody Pizza pizza) {
		pizzaService.createPizza(pizza);
	}

	@Override
	public Pizza getPizza(@PathVariable String name) {
		return pizzaService.getPizza(name);
	}

	@Override
	public List<Pizza> listPizza() {
		return pizzaService.listPizza();
	}
}
