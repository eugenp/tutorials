package com.bealdung.graphqlschemafirst.mutation;

import com.bealdung.graphqlschemafirst.entity.Vehicle;
import com.bealdung.graphqlschemafirst.model.VehicleRequest;
import com.bealdung.graphqlschemafirst.service.VehicleService;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


@Component
@Validated
public class VehicleMutation implements GraphQLMutationResolver {

    @Autowired
    private VehicleService vehicleService;

    public Vehicle createVehicle(VehicleRequest vehicleRequest) {
        return this.vehicleService.createVehicle(vehicleRequest);
    }

}
