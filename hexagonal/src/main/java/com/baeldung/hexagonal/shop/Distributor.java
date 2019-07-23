package com.baeldung.hexagonal.shop;

import com.baeldung.hexagonal.port.DistributorPort;
import com.baeldung.hexagonal.port.WarehousePort;
import com.baeldung.hexagonal.warehouse.FoodWarehouse;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class Distributor implements DistributorPort {

    private final WarehousePort warehouse;

    public Distributor() {
        this.warehouse = new FoodWarehouse();
    }

    public Distributor(WarehousePort warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<String> getProductList() {
        if (Objects.nonNull(warehouse)) {
            return warehouse.getProductsList();
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}
