package com.baeldung.adapter;

import com.baeldung.core.domain.Coffee;
import com.baeldung.port.CoffeeService;
import com.baeldung.web.CoffeeRest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coffee")
public class CoffeeRestController implements CoffeeRest {

	@Autowired
	private CoffeeService coffeeService;

	@Override
	public void createCoffee(@RequestBody Coffee coffee) {
		coffeeService.createCoffee(coffee);
	}

	@Override
	public Coffee getCoffee(@PathVariable String name) {
		return coffeeService.getCoffee(name);
	}

	@Override
	public List<Coffee> listCoffee() {
		return coffeeService.listCoffee();
	}
}
