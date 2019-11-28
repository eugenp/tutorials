package com.hex.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hex.domain.Car;
import com.hex.port.CarUIPort;
import com.hex.service.CarService;

@RestController
@RequestMapping("/cars/")
public class CarControllerAdapter implements CarUIPort{

    @Autowired
    private CarService carService;

   @Override
    public void create(@RequestBody Car request) {
        carService.create(request.getModel(), request.getMake(), request.getColor());
    }

    @Override
    public Car view(@PathVariable Integer id) {
        Car car = carService.view(id);
        return car;
    }
}