package com.baeldung.dddhexagonalquickexample.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.dddhexagonalquickexample.domain.Car;
import com.baeldung.dddhexagonalquickexample.domain.CarStore;
import com.baeldung.dddhexagonalquickexample.domain.ports.service.ICarStoreService;

@RestController
@RequestMapping("/carstore")
public class CarStoreController {

    private ICarStoreService carStoreService;

    @Autowired
    public CarStoreController(ICarStoreService carStoreService) {
        this.carStoreService = carStoreService;
    }

    @PostMapping
    void createStore(@RequestBody CarStore store) {
        carStoreService.createStore(store);
    }

    @GetMapping("/{id}")
    CarStore getStore(@PathVariable Long id){
        return carStoreService.getStore(id);
    }

    @PostMapping(value = "/{id}/car")
    void addCar(@PathVariable Long id, @RequestBody Car car) {
        carStoreService.addCarToCatalog(id, car);
    }

    @GetMapping("/{id}/cars")
    List<Car> getCatalog(@PathVariable Long id) {
        return carStoreService.getCars(id);
    }

    @GetMapping("/{id}/car/{idCar}")
    Car getCar(@PathVariable Long id, @PathVariable Long idCar) {
        return carStoreService.getCar(id, idCar);
    }

    @DeleteMapping(value = "/{id}/car/{idCar}")
    void sellCar(@PathVariable Long id, @PathVariable Long idCar) {
        carStoreService.sellCar(id, idCar);
    }
}