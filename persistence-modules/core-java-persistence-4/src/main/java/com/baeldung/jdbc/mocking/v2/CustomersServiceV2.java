package com.baeldung.jdbc.mocking.v2;

import java.util.List;
import java.util.function.Supplier;

import com.baeldung.jdbc.mocking.Customer;
import com.baeldung.jdbc.mocking.Customer.Status;

public class CustomersServiceV2 {

    private final Supplier<List<Customer>> findAllCustomers;

    public List<Customer> customersEligibleForOffers() {
        return findAllCustomers.get()
            .stream()
            .filter(customer -> customer.status() == Status.ACTIVE || customer.status() == Status.LOYAL)
            .toList();
    }

    public CustomersServiceV2(Supplier<List<Customer>> findAllCustomers) {
        this.findAllCustomers = findAllCustomers;
    }

}