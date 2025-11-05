package com.baeldung.quarkus.testcontainers;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderService {

    public List<Order> findByCustomerId(Long id) {
        return Order.find("customer.id = ?1", id)
            .list();
    }

}
