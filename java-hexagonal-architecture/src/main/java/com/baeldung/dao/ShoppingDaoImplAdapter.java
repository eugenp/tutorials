package com.baeldung.dao;

import java.util.Random;
import org.springframework.stereotype.Repository;

/**
 * DAO implementation class to fetch data from the database.
 */
@Repository
public class ShoppingDaoImplAdapter implements ShoppingDaoPort {

    Random random = new Random();

    @Override
    public long saveOrder(String productName) {
        long orderId = random.nextLong();
        return orderId;
    }

    @Override
    public void updateInventory() {
        // Update inventory table per the database implementation
    }

}
