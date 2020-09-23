package com.baeldung.pattern.hexagonal.architecture.adapters.web;

import com.baeldung.pattern.hexagonal.architecture.application.port.incoming.RentCarUseCase;
import com.baeldung.pattern.hexagonal.architecture.application.port.incoming.ReturnRentalUseCase;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car")
public class CarController {

    private final RentCarUseCase rentCarUseCase;
    private final ReturnRentalUseCase returnRentalUseCase;

    public CarController(RentCarUseCase rentCarUseCase, ReturnRentalUseCase returnRentalUseCase){
        this.rentCarUseCase = rentCarUseCase;
        this.returnRentalUseCase = returnRentalUseCase;
    }

    @PostMapping(value = "/{id}/rent")
    void rent(@PathVariable final Long id) {
        rentCarUseCase.rent(id);
    }

    @PostMapping(value = "/{id}/return-rental")
    void returnRental(@PathVariable final Long id) {
        returnRentalUseCase.returnRental(id);
    }
}
