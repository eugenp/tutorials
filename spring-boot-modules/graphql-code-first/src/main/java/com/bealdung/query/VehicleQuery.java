package com.bealdung.graphqlcodefirst.query;

import com.bealdung.graphqlcodefirst.entity.Vehicle;
import com.bealdung.graphqlcodefirst.service.VehicleService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@GraphQLApi
@Component
public class VehicleQuery{

    @Autowired
    private VehicleService vehicleService;

    @GraphQLQuery(name ="getAllVehicles")
    public List<Vehicle> getAllVehicles() {
        return this.vehicleService.getAllVehicles();
    }
    @GraphQLQuery(name="getVehicle")
    public Optional<Vehicle> getVehicle(@GraphQLArgument(name="id") final int id) {
        return this.vehicleService.getVehicle(id);
    }
}
