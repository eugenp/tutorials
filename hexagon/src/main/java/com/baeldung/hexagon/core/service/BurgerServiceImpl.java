package com.baeldung.hexagon.core.service;

import com.baeldung.hexagon.core.domain.Burger;
import com.baeldung.hexagon.port.inbound.BurgerService;
import com.baeldung.hexagon.port.outbound.BurgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BurgerServiceImpl implements BurgerService {

    @Autowired
    private BurgerRepository burgerRepository;

    @Override
    public void createBurger(Burger burger) {
        burgerRepository.createBurger(burger);
    }

    @Override
    public Burger findByName(String name) {
        return burgerRepository.findByName(name);
    }

    @Override
    public List<Burger> findAll() {
        return burgerRepository.findAll();
    }
}
