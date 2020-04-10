package com.baeldung.storedprocedure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.storedprocedure.entity.Car;
import com.baeldung.storedprocedure.service.CarService;

@RestController
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping(path = "/modelcount")
    public long getTotalCarsByModel(@RequestParam("model") String model) {
        return carService.getTotalCarsByModel(model);
    }

    @GetMapping(path = "/modelcountP")
    public long getTotalCarsByModelProcedureName(@RequestParam("model") String model) {
        return carService.getTotalCarsByModelProcedureName(model);
    }
    
    @GetMapping(path = "/modelcountV")
    public long getTotalCarsByModelVaue(@RequestParam("model") String model) {
        return carService.getTotalCarsByModelValue(model);
    }
    
    @GetMapping(path = "/modelcountEx")
    public long getTotalCarsByModelExplicit(@RequestParam("model") String model) {
        return carService.getTotalCarsByModelExplicit(model);
    }
    
    @GetMapping(path = "/modelcountEn")
    public long getTotalCarsByModelEntity(@RequestParam("model") String model) {
        return carService.getTotalCarsByModelEntity(model);
    }
    
    @GetMapping(path = "/carsafteryear")
    public List<Car> findCarsAfterYear(@RequestParam("year") Integer year) {
        return carService.findCarsAfterYear(year);
    }
}
