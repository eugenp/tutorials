package com.baeldung.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.model.Burger;

@Repository
public class BurgerRepositoryImpl implements BurgerRepository {

	private Map<String, Burger> burgerStore = new HashMap<>();

	@Override
	public void createBurger(Burger burger) {
		burgerStore.put(burger.getName(), burger);
	}

	@Override
	public Burger getBurger(String burgerName) {
		return burgerStore.get(burgerName);
	}

	@Override
	public List<Burger> getAllBurger() {
		return burgerStore.values().stream().collect(Collectors.toList());
	}
}
