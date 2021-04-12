package com.baeldung.adapters;

import java.util.Arrays;
import java.util.List;

import com.baeldung.domain.Car;
import com.baeldung.ports.CarDao;

public class CarDaoMock implements CarDao {

    List<Car> cars = Arrays.asList(new Car(1, "Nissan"), new Car(2, "Citroen"));

    @Override
    public Car getById(int id) {
        return cars.stream()
            .filter(car -> car.getId() == id)
            .findAny()
            .orElse(null);
    }

    @Override
    public Car getByName(String name) {
        return cars.stream()
            .filter(car -> car.getName()
                .equals(name))
            .findAny()
            .orElse(null);
    }

}
