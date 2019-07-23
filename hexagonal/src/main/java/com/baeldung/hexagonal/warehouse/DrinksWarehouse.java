package com.baeldung.hexagonal.warehouse;

import com.baeldung.hexagonal.port.WarehousePort;

import java.util.ArrayList;
import java.util.Collection;

public class DrinksWarehouse implements WarehousePort {

    @Override
    @SuppressWarnings("unchecked")
    public Collection<String> getProductsList() {

        final Collection<String> productList = new ArrayList();
        productList.add("English wine");
        productList.add("Rum");
        productList.add("Champagne");

        return productList;
    }
}
