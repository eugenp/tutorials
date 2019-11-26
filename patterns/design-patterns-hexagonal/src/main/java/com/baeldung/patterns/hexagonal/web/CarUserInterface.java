package com.baeldung.patterns.hexagonal.web;

import com.baeldung.patterns.hexagonal.domain.Car;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CarUserInterface {

    @PostMapping
    void build(@RequestBody Car car);

    @GetMapping("/{model}")
    Car select(@PathVariable String model);

    @GetMapping
    List<Car> all();

}
