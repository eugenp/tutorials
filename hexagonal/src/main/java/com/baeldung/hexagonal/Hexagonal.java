package com.baeldung.hexagonal;

import com.baeldung.hexagonal.port.DistributorPort;
import com.baeldung.hexagonal.shop.Distributor;
import com.baeldung.hexagonal.warehouse.DrinksWarehouse;

public class Hexagonal {

    public static void main(String[] args) {

        final DistributorPort foodDistributor = new Distributor();
        System.out.println(foodDistributor.getProductList());

        final DistributorPort drinksDistributor = new Distributor(new DrinksWarehouse());
        System.out.println(drinksDistributor.getProductList());

    }

}
