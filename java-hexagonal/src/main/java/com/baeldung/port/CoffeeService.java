package com.baeldung.port;

import java.util.List;

import com.baeldung.core.domain.Coffee;

public interface CoffeeService {
	
	public void createCoffee(Coffee coffee);
	
	public Coffee getCoffee(String name);
	
	public List<Coffee> listCoffee();

}
