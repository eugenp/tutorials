package com.baeldung.springboothexagonal.application;

import com.baeldung.springboothexagonal.domain.Vehicle;
import com.baeldung.springboothexagonal.domain.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetVehiclesService implements GetVehicles {

    private final VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> getVehicles() {
        return vehicleRepository.loadAllVehicles();
    }
}
