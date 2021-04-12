package com.baeldung.adapters;

import com.baeldung.domain.Car;
import com.baeldung.ports.CarApi;
import com.baeldung.service.CarService;

public class CarApiHttp implements CarApi {

    private CarService carService;

    public CarApiHttp(CarService carService) {
        this.carService = carService;
    }

    @Override
    public Car getById(int id) {
        // TODO Implement HTTP getById end point
        return carService.find(id);
    }

    @Override
    public Car getByName(String name) {
        // TODO Implement HTTP getByName end point
        return carService.find(name);
    }

}
