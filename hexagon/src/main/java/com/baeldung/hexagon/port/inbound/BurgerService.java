package com.baeldung.hexagon.port.inbound;

import com.baeldung.hexagon.core.domain.Burger;

import java.util.List;

public interface BurgerService {

    void createBurger(Burger burger);

    Burger findByName(String name);

    List<Burger> findAll();

}
