package com.baeldung.service;

import java.util.List;

import com.baeldung.model.Burger;

public interface BurgerService {
	public void createBurger(Burger burger);

	public Burger getBurger(String burgerName);

	public List<Burger> listBurger();
}
