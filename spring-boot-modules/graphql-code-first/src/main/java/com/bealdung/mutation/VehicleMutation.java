package com.bealdung.graphqlcodefirst.mutation;

import com.bealdung.graphqlcodefirst.entity.Vehicle;
import com.bealdung.graphqlcodefirst.model.VehicleRequest;
import com.bealdung.graphqlcodefirst.service.VehicleService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@GraphQLApi
@Component
public class VehicleMutation {

    @Autowired
    private VehicleService vehicleService;

    @GraphQLMutation
    public Vehicle createVehicle(@GraphQLArgument(name="vehicleRequest") VehicleRequest vehicleRequest) {
        return this.vehicleService.createVehicle(vehicleRequest);
    }

}
