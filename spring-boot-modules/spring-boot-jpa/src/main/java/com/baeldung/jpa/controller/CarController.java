package com.baeldung.jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.jpa.entity.Car;
import com.baeldung.jpa.service.CarService;

@RestController
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping(path = "/modelcount")
    public long getTotalCarsByModel(@RequestParam("model") String model) {
        return carService.getTotalCarsByModel(model);
    }

    @GetMapping(path = "/carsafteryear")
    public List<Car> findCarsAfterYear(@RequestParam("year") Integer year) {
        return carService.findCarsAfterYear(year);
    }
}
