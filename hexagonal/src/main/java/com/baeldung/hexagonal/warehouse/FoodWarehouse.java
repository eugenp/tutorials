package com.baeldung.hexagonal.warehouse;

import com.baeldung.hexagonal.port.WarehousePort;

import java.util.ArrayList;
import java.util.Collection;

public class FoodWarehouse implements WarehousePort {

    @Override
    @SuppressWarnings("unchecked")
    public Collection<String> getProductsList() {

        Collection<String> productList = new ArrayList();
        productList.add("Fat free milk");
        productList.add("Pork");
        productList.add("Frozen breakfast");

        return productList;
    }
}
