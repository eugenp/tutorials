package com.baeldung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.model.Burger;
import com.baeldung.service.BurgerService;

@RestController
@RequestMapping("/burger")
public class BurgerRestController implements BurgerRestUI {
	@Autowired
	private BurgerService burgerService;

	@Override
	public void createBurger(Burger burger) {
		burgerService.createBurger(burger);
	}

	@Override
	public Burger getBurger(String burgerName) {
		return burgerService.getBurger(burgerName);
	}

	@Override
	public List<Burger> listBurger() {
		return burgerService.listBurger();
	}
}
