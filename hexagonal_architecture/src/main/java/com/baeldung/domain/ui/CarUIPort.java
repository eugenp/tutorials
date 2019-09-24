package com.baeldung.domain.ui;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.domain.Car;


public interface CarUIPort {

	void saveFromUI(@RequestBody Car request);
	
	List<Car> listAllCarsFromUI();
}
