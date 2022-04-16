package com.bealdung.graphqlschemafirst.query;

import com.bealdung.graphqlschemafirst.entity.Vehicle;
import com.bealdung.graphqlschemafirst.service.VehicleService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VehicleQuery implements GraphQLQueryResolver {

    @Autowired
    private VehicleService vehicleService;

    public List<Vehicle> getAllVehicles() {
        return this.vehicleService.getAllVehicles();
    }

    public Optional<Vehicle> getVehicle(final int id) {
        return this.vehicleService.getVehicle(id);
    }
}
