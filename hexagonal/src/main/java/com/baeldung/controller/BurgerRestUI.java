package com.baeldung.controller;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.model.Burger;

@Component
public interface BurgerRestUI {
	@PostMapping
	void createBurger(@RequestBody Burger burger);

	@GetMapping("/{name}")
	public Burger getBurger(@PathVariable String name);

	@GetMapping
	public List<Burger> listBurger();

}
