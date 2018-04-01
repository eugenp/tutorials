package org.disco.racer.domain;

import java.util.Collection;

interface RaceDAO {

    Collection<Race> findAll();

    Race findById(Long id);

    Race findByName(String name);

    void create(Race race);

    void update(Race race);

    void remove(Race race);
}
