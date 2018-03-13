package com.domain;


import java.util.HashSet;
import java.util.Set;

public class Customer {
    private Long id;
    private String name;
    private Set<Order> orders = new HashSet<Order>();

    public Customer() {
    }


    public Customer(String name, Set<Order> orders) {
        this.name = name;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
