package com.baeldung.graphql.error.handling.controller;

import com.baeldung.graphql.error.handling.domain.Location;
import com.baeldung.graphql.error.handling.domain.Vehicle;
import com.baeldung.graphql.error.handling.service.InventoryService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VehicleController {

    private final InventoryService inventoryService;

    public VehicleController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @QueryMapping
    public List<Vehicle> searchAll() {
        return this.inventoryService.searchAll();
    }

    @QueryMapping
    public List<Vehicle> searchByLocation(@Argument String zipcode) {
        return this.inventoryService.searchByLocation(zipcode);
    }

    @QueryMapping
    public Vehicle searchByVin(@Argument String vin) {
        return this.inventoryService.searchByVin(vin);
    }

    @MutationMapping
    public Vehicle addVehicle(@Argument String vin, @Argument Integer year,
      @Argument String make, @Argument String model, @Argument String trim,
      @Argument Location location) {
        return this.inventoryService.addVehicle(vin, year, make, model, trim, location);
    }
}
