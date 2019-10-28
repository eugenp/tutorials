package com.baeldung.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.core.domain.Coffee;

public interface CoffeeRest {
	
	@PostMapping
	void createCoffee(@RequestBody Coffee coffee);
	

	@GetMapping("/{name}")
	public Coffee getCoffee(@PathVariable String name);

	@GetMapping
	public List<Coffee> listCoffee() ;

}
