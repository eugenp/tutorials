package com.baeldung.graphql.error.handling.resolver;

import com.baeldung.graphql.error.handling.domain.Vehicle;
import com.baeldung.graphql.error.handling.service.InventoryService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver  {
    private final InventoryService inventoryService;

    public Query(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public List<Vehicle> searchAll() {
        return this.inventoryService.searchAll();
    }

    public List<Vehicle> searchByLocation(String zipcode) {
        return this.inventoryService.searchByLocation(zipcode);
    }

    public Vehicle searchByVin(String vin) {
        return this.inventoryService.searchByVin(vin);
    }
}
