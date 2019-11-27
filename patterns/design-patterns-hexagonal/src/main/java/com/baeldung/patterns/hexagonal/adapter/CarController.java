package com.baeldung.patterns.hexagonal.adapter;


import com.baeldung.patterns.hexagonal.domain.Car;
import com.baeldung.patterns.hexagonal.port.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carhouse")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping
    public void build(@RequestBody Car car) {
        carService.buildCar(car);
    }

    @GetMapping("/{model}")
    public Car select(@PathVariable String model) {
        return carService.selectCar(model);
    }

    @GetMapping
    public List<Car> all() {
        return carService.allModels();
    }

}