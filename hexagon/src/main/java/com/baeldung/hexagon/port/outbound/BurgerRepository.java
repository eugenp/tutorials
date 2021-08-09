package com.baeldung.hexagon.port.outbound;

import com.baeldung.hexagon.core.domain.Burger;

import java.util.List;

public interface BurgerRepository {
    void createBurger(Burger burger);

    Burger findByName(String name);

    List<Burger> findAll();
}
