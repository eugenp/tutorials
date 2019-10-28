package com.baeldung.core.impl;

import com.baeldung.port.CoffeeRepository;
import com.baeldung.port.CoffeeService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.core.domain.Coffee;

@Service
public class CoffeeServiceImpl implements CoffeeService {

	@Autowired
	private CoffeeRepository coffeeRepository;

	@Override
	public void createCoffee(Coffee coffee) {
		coffeeRepository.createCoffee(coffee);
	}

	@Override
	public Coffee getCoffee(String name) {

		return coffeeRepository.getCoffee(name);
	}

	@Override
	public List<Coffee> listCoffee() {
		return coffeeRepository.getAllCoffee();
	}

}
