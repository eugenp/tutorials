package com.baeldung.ruleengine.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    String name;
    int loyaltyPoints;
    boolean firstOrder;

    public Customer() {
    }

    public Customer(String name, int loyaltyPoints, boolean firstOrder) {
        this.name = name;
        this.loyaltyPoints = loyaltyPoints;
        this.firstOrder = firstOrder;
    }

}
