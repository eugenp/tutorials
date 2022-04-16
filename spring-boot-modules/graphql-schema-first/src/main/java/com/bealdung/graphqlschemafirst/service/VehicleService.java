package com.bealdung.graphqlschemafirst.service;

import com.bealdung.graphqlschemafirst.entity.Vehicle;
import com.bealdung.graphqlschemafirst.model.VehicleRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bealdung.graphqlschemafirst.repository.VehicleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository ;

    public VehicleService(final VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository ;
    }

    @Transactional
    public Vehicle createVehicle(VehicleRequest vehicleRequest) {
        final Vehicle vehicle = new Vehicle();
        vehicle.setType(vehicleRequest.getType());
        vehicle.setModelCode(vehicleRequest.getModelCode());
        vehicle.setBrandName(vehicleRequest.getBrandName());
        vehicle.setLaunchDate(LocalDate.parse(vehicleRequest.getLaunchDate()));
        return this.vehicleRepository.save(vehicle);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles() {
        return this.vehicleRepository.findAll().stream().collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Vehicle> getVehicle(final int id) {
        return this.vehicleRepository.findById(id);
    }
}
