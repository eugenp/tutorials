package com.baeldung.hexagonal.store.core.context.customer.entity;

import com.baeldung.hexagonal.store.core.context.order.entity.Order;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Customer {
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

    public boolean isNegativeBalanceAllowed() {
        return true;
    }

    public void withdrawFunds(Double amount) {
        this.balance -= amount;
    }

    public boolean hasEnoughFunds(Double amount) {
        return this.balance >= amount;
    }

    public void topUpFunds(Double amount) {
        this.balance += amount;
    }

}
