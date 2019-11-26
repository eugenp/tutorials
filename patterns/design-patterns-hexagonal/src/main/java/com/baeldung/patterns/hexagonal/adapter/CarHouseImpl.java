package com.baeldung.patterns.hexagonal.adapter;


import com.baeldung.patterns.hexagonal.domain.Car;
import com.baeldung.patterns.hexagonal.port.CarHouse;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CarHouseImpl implements CarHouse {

    private Map<String, Car> carShop = new HashMap<>();

    @Override
    public void buildCar(Car car) {
        carShop.put(car.getModel(), car);
    }

    @Override
    public Car selectCar(String model) {
        return carShop.get(model);
    }

    @Override
    public List<Car> allModels() {
        return carShop.values().stream().collect(Collectors.toList());
    }

}