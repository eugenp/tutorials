package com.baeldung.applications.spring.rest;

import com.baeldung.applications.spring.rest.dto.CrudCarResponse;
import com.baeldung.applications.spring.rest.dto.CrudCarRequest;
import com.baeldung.domain.entities.Car;
import com.baeldung.domain.ports.in.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestScope
@RequestMapping("/spring-rest/car")
public class CarRestController {

    @Autowired
    private CarService carService;

    @RequestMapping(
            value = "/get/{vin}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Car getCar(@PathVariable String vin) {
        return carService.getCar(vin);
    }

    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public CrudCarResponse createCar(@RequestBody CrudCarRequest createCarRequest) {
        //validate the request object

        Boolean success = carService.createOrUpdateCar(createCarRequest.getCar());
        return new CrudCarResponse(success, (success != true) ? "Failed to create a new car" : "");
    }

    @RequestMapping(
            value = "/update",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public CrudCarResponse updateCar(@RequestBody CrudCarRequest updateCarRequest) {
        //validate the request object

        Boolean success = carService.createOrUpdateCar(updateCarRequest.getCar());
        return new CrudCarResponse(success, (success != true) ? "Failed to update car" : "");
    }

    @RequestMapping(
            value = "/delete/{vin}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public CrudCarResponse deleteCar(@PathVariable String vin) {
        Boolean success = carService.deleteCar(vin);
        return new CrudCarResponse(success, (success != true) ? "Failed to delete car" : "");
    }
}
