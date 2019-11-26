package com.baeldung.patterns.hexagonal.domain;


import com.baeldung.patterns.hexagonal.domain.Car;
import com.baeldung.patterns.hexagonal.port.CarHouse;
import com.baeldung.patterns.hexagonal.port.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarHouse carHouse;

    @Override
    public void buildCar(Car car) {
        carHouse.buildCar(car);
    }

    @Override
    public Car selectCar(String model) {
        return carHouse.selectCar(model);
    }

    @Override
    public List<Car> allModels() {
        return carHouse.allModels();
    }

}
