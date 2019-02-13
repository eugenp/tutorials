package com.baeldung.hexagonal.business;

import java.util.Optional;

public interface TemperatureRepository {

        Optional<Integer> getTemperature(String city);
}
