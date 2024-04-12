package com.baeldung.spring.data.cassandra.test.service;

import com.baeldung.spring.data.cassandra.test.domain.Vehicle;
import com.baeldung.spring.data.cassandra.test.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Vehicle> getVehicles() {
        return this.inventoryRepository.findAllVehicles();
    }

    public Vehicle getVehicle(String vin) {
        return this.inventoryRepository.findByVin(vin).orElse(null);
    }

    public void addVehicles(List<Vehicle> vehicles) {
        this.inventoryRepository.saveAll(vehicles);
    }

    public void updateVehicles(List<Vehicle> vehicles) {
        this.inventoryRepository.saveAll(vehicles);
    }

    public void updateVehicle(String vin, Vehicle vehicle) {
        Vehicle existingVehicle = this.inventoryRepository.findByVin(vin)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        existingVehicle.setMake(vehicle.getMake());
        existingVehicle.setYear(vehicle.getYear());
        existingVehicle.setModel(vehicle.getModel());

        this.inventoryRepository.save(existingVehicle);
    }

    public void deleteVehicle(String vin) {
        this.inventoryRepository.deleteByVin(vin);
    }
}
