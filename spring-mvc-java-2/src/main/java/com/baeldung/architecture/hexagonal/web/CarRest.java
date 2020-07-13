package com.baeldung.architecture.hexagonal.web;

import com.baeldung.architecture.hexagonal.core.domain.Car;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CarRest {
    @PostMapping
    void createCar(@RequestBody Car car);

    @GetMapping("/{id}")
    Car getCar(@PathVariable Integer id);

    @GetMapping
    List<Car> getCars();
}
