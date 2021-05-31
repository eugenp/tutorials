package com.baeldung.springboothexagonal.adapter.in.web.rest;

import com.baeldung.springboothexagonal.application.GetVehicles;
import com.baeldung.springboothexagonal.domain.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class VehicleRestController {

    private final GetVehicles getVehicles;

    @GetMapping("/vehicles")
    List<Vehicle> getVehicles() {
        return getVehicles.getVehicles();
    }
}
