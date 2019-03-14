package com.baeldung.hexagonal.store.core.context.customer.entity;

import com.baeldung.hexagonal.store.core.context.order.entity.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer implements StoreCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    String firstname, lastname;
    String email;
    Double balance;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Order> orders = new ArrayList<>();

    @Override
    public boolean isNegativeBalanceAllowed() {
        return true;
    }

    @Override
    public Double getBalance() {
        return balance;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Long getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void withdrawFunds(Double sum) {
        this.balance -= sum;
    }

    public boolean hasEnoughFunds(Double amount) {
        return this.balance >= amount;
    }

    public void topUpFunds(Double sum) {
        this.balance += sum;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }
}
