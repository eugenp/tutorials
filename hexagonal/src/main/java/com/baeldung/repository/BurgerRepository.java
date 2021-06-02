package com.baeldung.repository;

import java.util.List;

import com.baeldung.model.Burger;

public interface BurgerRepository {
	public void createBurger(Burger burger);

	public Burger getBurger(String burgerName);

	public List<Burger> getAllBurger();
}
