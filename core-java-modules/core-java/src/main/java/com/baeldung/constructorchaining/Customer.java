package com.baeldung.constructorchaining;

import java.util.Objects;

public class Customer extends Person {
    private final String loyaltyCardId;

    public Customer(String firstName, String lastName, int age, String loyaltyCardId) {
        this(firstName, null, lastName, age, loyaltyCardId);
    }

    public Customer(String firstName, String middleName, String lastName, int age, String loyaltyCardId) {
        super(firstName, middleName, lastName, age);
        this.loyaltyCardId = loyaltyCardId;
    }

    public String getLoyaltyCardId() {
        return loyaltyCardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(loyaltyCardId, customer.loyaltyCardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loyaltyCardId);
    }
}
