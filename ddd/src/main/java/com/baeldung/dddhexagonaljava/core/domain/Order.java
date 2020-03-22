package com.baeldung.dddhexagonaljava.core.domain;

import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private List<ProductOrder> productOrders;
    private Customer customer;

    public Order(UUID id, List<ProductOrder> productOrders, Customer customer) {
        this.id = id;
        this.productOrders = productOrders;
        this.customer = customer;
    }

    public UUID getId() {
        return id;
    }

    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public Customer getCustomer() {
        return customer;
    }
}