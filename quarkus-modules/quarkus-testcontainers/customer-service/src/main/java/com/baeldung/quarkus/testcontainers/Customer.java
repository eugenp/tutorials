package com.baeldung.quarkus.testcontainers;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.quarkus.testcontainers.client.Order;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class Customer extends PanacheEntity {

    public String name;

    @Transient
    public List<Order> orders = new ArrayList<>();

    public static Customer findById(Long id) {
        return find("id", id).firstResult();
    }

    public void addOrders(List<Order> orders) {
        this.orders.addAll(orders);
    }

}
