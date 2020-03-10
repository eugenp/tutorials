package com.technopreneur.patterns.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.technopreneur.patterns.core.domain.Pizza;

public interface PizzaRestUI {
	
	@PostMapping
	void createPizza(@RequestBody Pizza pizza);
	

	@GetMapping("/{name}")
	public Pizza getPizza(@PathVariable String name);

	@GetMapping
	public List<Pizza> listPizza() ;

}
