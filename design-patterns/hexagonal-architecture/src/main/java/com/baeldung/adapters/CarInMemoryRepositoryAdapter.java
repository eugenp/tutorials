package com.baeldung.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.baeldung.domain.Car;
import com.baeldung.ports.CarRepositoryPort;

public class CarInMemoryRepositoryAdapter implements CarRepositoryPort {
    private Collection<Car> availableCars;

    public CarInMemoryRepositoryAdapter() {
        availableCars = new ArrayList<>();

        availableCars.add(new Car("FERRARI", "812 Superfast"));
        availableCars.add(new Car("FERRARI", "GTC4 Lusso"));
        availableCars.add(new Car("FERRARI", "Portofino"));
    }

    public Collection<Car> getAll() {
        return new ArrayList<>(availableCars);
    }

    public Optional<Car> get(String manufacturer, String model) {
        Car car = new Car(manufacturer, model);

        return availableCars.stream()
            .filter(availableCar -> availableCar.equals(car))
            .findFirst();
    }

    public void remove(Car car) {
        availableCars.remove(car);
    }
}
