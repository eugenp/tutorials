package com.baeldung.patterns.hexagonal.port;


import com.baeldung.patterns.hexagonal.domain.Car;

import java.util.List;

public interface CarService {

    void buildCar(Car car);

    Car selectCar(String model);

    List<Car> allModels();

}
