package com.baeldung.spring.hexagon.port;

import java.util.List;

import com.baeldung.spring.hexagon.domain.SuperHero;

public interface Service {
    public List<SuperHero> getAll();

    public void save(SuperHero hero);

    public SuperHero fight(int id1, int id2);

    public SuperHero getSuperHeroById(int id);

    public void delete(int id);
}
