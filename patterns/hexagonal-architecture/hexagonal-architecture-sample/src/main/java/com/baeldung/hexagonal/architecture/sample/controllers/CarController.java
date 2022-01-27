package com.baeldung.hexagonal.architecture.sample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.architecture.sample.model.Car;
import com.baeldung.hexagonal.architecture.sample.services.CarServiceImpl;

@RestController("/cars")
public class CarController {
	@Autowired
	private CarServiceImpl carService;

	public CarController(CarServiceImpl carService) {
		this.carService = carService;
	}

	@GetMapping("/{id}")
	public Car getCar(@PathVariable("id") String id) {
		return carService.getCar(id);
	}

	@GetMapping("/{id}/plate")
	public String getPlate(@PathVariable("id") String id) {
		return carService.getPlate(id);
	}

	@PostMapping("/add")
	public Car addCar(@RequestBody Car car) {
		return carService.addCar(car);
	}

}
