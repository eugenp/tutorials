package org.baeldung.hex.adapters.primary;

import lombok.RequiredArgsConstructor;
import org.baeldung.hex.domain.dto.Car;
import org.baeldung.hex.ports.inbound.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping("/{vin}")
    public ResponseEntity<Car> getCarByVin(@PathVariable String vin) {
        return new ResponseEntity<Car>(carService.getCar(vin), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addCar(@RequestBody Car car) {
        carService.addCar(car);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

}
