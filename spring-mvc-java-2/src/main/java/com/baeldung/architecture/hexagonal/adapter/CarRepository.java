package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.core.domain.Car;
import com.baeldung.architecture.hexagonal.port.CarRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CarRepository implements CarRepositoryPort {
    private Map<Integer, Car> carStore = new HashMap<>();

    @Override
    public void createCar(Car car) {
        carStore.put(car.getId(), car);
    }

    @Override
    public Car getCar(int id) {
        return carStore.get(id);
    }

    @Override
    public List<Car> getCars() {
        return carStore.values()
                .stream()
                .collect(Collectors.toList());
    }
}
