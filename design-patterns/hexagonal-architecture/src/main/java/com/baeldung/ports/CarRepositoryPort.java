package com.baeldung.ports;

import java.util.Collection;
import java.util.Optional;

import com.baeldung.domain.Car;

public interface CarRepositoryPort {
    Collection<Car> getAll();

    Optional<Car> get(String manufacturer, String model);

    void remove(Car car);
}
