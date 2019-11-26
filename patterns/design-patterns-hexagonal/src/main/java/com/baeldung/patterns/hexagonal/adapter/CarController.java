package com.baeldung.patterns.hexagonal.adapter;


import com.baeldung.patterns.hexagonal.domain.Car;
import com.baeldung.patterns.hexagonal.port.CarService;
import com.baeldung.patterns.hexagonal.web.CarUserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carhouse")
public class CarController implements CarUserInterface {

    @Autowired
    private CarService carService;

    @Override
    public void build(@RequestBody Car car) {
        carService.buildCar(car);
    }

    @Override
    public Car select(@PathVariable String model) {
        return carService.selectCar(model);
    }

    @Override
    public List<Car> all() {
        return carService.allModels();
    }

}