package com.baeldung.port;

import java.util.List;

import com.baeldung.core.domain.Coffee;

public interface CoffeeRepository {
	
	void createCoffee(Coffee coffee);
	
	Coffee getCoffee(String name);
	
	List<Coffee> getAllCoffee();

}
