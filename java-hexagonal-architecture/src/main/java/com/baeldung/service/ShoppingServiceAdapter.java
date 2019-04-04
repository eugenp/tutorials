package com.baeldung.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.dao.ShoppingDaoPort;
import com.baeldung.domain.Order;

/**
 * Shopping service implementation to handle core logic of the application.
 *
 */
@Service
public class ShoppingServiceAdapter implements ShoppingServicePort {

    @Autowired
    ShoppingDaoPort shoppingDaoPort;

    @Override
    public Order placeOrder(String productName) {
        long orderId = shoppingDaoPort.saveOrder(productName);
        return new Order(orderId, productName);
    }

    @Override
    public void updateInventory() {
        shoppingDaoPort.updateInventory();
    }

}
