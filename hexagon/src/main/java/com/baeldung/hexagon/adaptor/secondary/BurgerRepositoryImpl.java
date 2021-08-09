package com.baeldung.hexagon.adaptor.secondary;

import com.baeldung.hexagon.core.domain.Burger;
import com.baeldung.hexagon.port.outbound.BurgerRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BurgerRepositoryImpl implements BurgerRepository {

    private Map<String, Burger> burgerShop = new HashMap<>();

    @Override
    public void createBurger(Burger burger) {
        burgerShop.put(burger.getName(), burger);
    }

    @Override
    public Burger findByName(String name) {
        return burgerShop.get(name);
    }

    @Override
    public List<Burger> findAll() {
        return new ArrayList<>(burgerShop.values());
    }
}
