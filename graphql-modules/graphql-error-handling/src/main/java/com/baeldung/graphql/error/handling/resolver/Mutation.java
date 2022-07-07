package com.baeldung.graphql.error.handling.resolver;

import com.baeldung.graphql.error.handling.domain.Location;
import com.baeldung.graphql.error.handling.domain.Vehicle;
import com.baeldung.graphql.error.handling.service.InventoryService;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class Mutation implements GraphQLMutationResolver {
    private InventoryService inventoryService;

    public Mutation(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public Vehicle addVehicle(String vin, Integer year, String make, String model, String trim, Location location) {
        return this.inventoryService.addVehicle(vin, year, make, model, trim, location);
    }
}
