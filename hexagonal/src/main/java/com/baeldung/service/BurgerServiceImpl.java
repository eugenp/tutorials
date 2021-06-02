package com.baeldung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.model.Burger;
import com.baeldung.repository.BurgerRepository;

@Service
public class BurgerServiceImpl implements BurgerService {

	@Autowired
	private BurgerRepository burgerRepository;

	@Override
	public void createBurger(Burger burger) {
		burgerRepository.createBurger(burger);
	}

	@Override
	public Burger getBurger(String burgerName) {
		return burgerRepository.getBurger(burgerName);
	}

	@Override
	public List<Burger> listBurger() {
		return burgerRepository.getAllBurger();
	}

}
