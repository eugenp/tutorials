package com.baeldung.ruleengine.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
public class Order {

    private Double amount;
    private Customer customer;

    public Order() {
    }

    public Order(Double amount, Customer customer) {
        this.amount = amount;
        this.customer = customer;
    }
}
