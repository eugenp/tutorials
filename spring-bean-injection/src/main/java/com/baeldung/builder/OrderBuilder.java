package com.baeldung.builder;

import com.baeldung.model.Order;

public class OrderBuilder {
    public Order getOrder(int customerId){
        Order order = new Order(); //order to be fetched from backend
        order.setOrderNumber("A1233");
        order.setPrice(123.50);
        order.setProductName("Product Name");
        return order;
    }
}
