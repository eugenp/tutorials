package com.baeldung.hexagonalarchitecture.infrastructure.repository;

import com.baeldung.hexagonalarchitecture.domain.Temperature;
import com.baeldung.hexagonalarchitecture.domain.TemperatureRepository;

import java.util.HashMap;
import java.util.Map;

public class TemperatureRepositoryInMemory implements TemperatureRepository {

    private Map<String, Temperature> temperatureMap = new HashMap<>();

    @Override public void store(Temperature temperature) {
        temperatureMap.put(temperature.getLocation(), temperature);
    }
}
