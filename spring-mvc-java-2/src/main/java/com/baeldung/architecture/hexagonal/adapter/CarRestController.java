package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.core.domain.Car;
import com.baeldung.architecture.hexagonal.port.CarServicePort;
import com.baeldung.architecture.hexagonal.web.CarRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarRestController implements CarRest {
    @Autowired
    private CarServicePort carService;

    @Override
    public void createCar(Car car) {
        carService.createCar(car);
    }

    @Override
    public Car getCar(Integer id) {
        return carService.getCar(id);
    }

    @Override
    public List<Car> getCars() {
        return carService.getCars();
    }
}
