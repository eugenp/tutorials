package com.baeldung.applications.spring.rest;

import com.baeldung.domain.entities.Car;
import com.baeldung.domain.ports.in.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestScope
@RequestMapping("/car")
public class CarRestController {

    @Autowired
    private CarService carService;

    @RequestMapping(value = "/get/{vin}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Car getCar(@PathVariable String vin) {
        return carService.getCar(vin);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Boolean saveCar(String vin, String brand, Short modelYear) {
        //validate the request object

        Car car = new Car();
        car.setVin(vin);
        car.setBrand(brand);
        car.setModelYear(modelYear);
        return carService.saveCar(car);
    }
}
