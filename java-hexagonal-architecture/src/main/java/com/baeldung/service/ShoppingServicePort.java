package com.baeldung.service;

import com.baeldung.domain.Order;

/**
 * Shopping service interface to handle requests from controllers.
 */
public interface ShoppingServicePort {

    public Order placeOrder(String productName);

    public void updateInventory();

}
