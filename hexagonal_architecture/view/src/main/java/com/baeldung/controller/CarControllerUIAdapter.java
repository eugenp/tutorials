package com.baeldung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.domain.Car;
import com.baeldung.domain.ui.CarUIPort;
import com.baeldung.persistence.enums.CarEnum;
import com.baeldung.persistence.in_memory.factory.CarFactory;

@RestController
@RequestMapping("/cars/")
public class CarControllerUIAdapter implements CarUIPort{

    @Autowired
    private CarFactory carFactory;

    @PostMapping("save")
    public void saveFomUI(@RequestBody Car request) {
    	carFactory.getConnection(
    				CarEnum.MEMORY.name())
    	            .saveCar(request.getBrand(), request.getYear());
    }
    
    @GetMapping("listAllCars")
    public List<Car> listAllCarsFromUI() {
    	List<Car> cars = carFactory.getConnection(
    			CarEnum.MEMORY.name())
    			.listAllCars();
        return cars;
    }

	
}