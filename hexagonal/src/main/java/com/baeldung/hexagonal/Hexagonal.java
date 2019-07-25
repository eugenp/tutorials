package com.baeldung.hexagonal;

import com.baeldung.hexagonal.port.DistributorPort;
import com.baeldung.hexagonal.shop.Distributor;
import com.baeldung.hexagonal.warehouse.DrinksWarehouse;

public class Hexagonal  {

    public static void main(String[] args) {

        DistributorPort foodDistributor = new Distributor();
        foodDistributor.getProductList();

        DistributorPort drinksDistributor = new Distributor(new DrinksWarehouse());
        drinksDistributor.getProductList();

    }

}
