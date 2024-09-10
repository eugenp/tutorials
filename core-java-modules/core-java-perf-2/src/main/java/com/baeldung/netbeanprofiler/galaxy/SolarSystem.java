package com.baeldung.netbeanprofiler.galaxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SolarSystem {

    private static final Logger LOGGER = LoggerFactory.getLogger(SolarSystem.class);

    private int id;

    private String name;

    private List<String> planet = new ArrayList<>();

    public SolarSystem() {
    }

    public SolarSystem(int id, String name, List<String> planet) {
        this.id = id;
        this.name = name;
        this.planet = planet;
    }

    public void removePlanet(String name) {
        planet.remove(name);
    }

    public void logSolarSystem() {
        LOGGER.info(name);
        LOGGER.info(String.valueOf(id));
        LOGGER.info(planet.toString());
    }
}
