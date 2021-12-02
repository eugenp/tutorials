package com.baeldung.hexagonal.adapter.persistence.repositories;

import com.baeldung.hexagonal.domain.model.Customer;
import com.baeldung.hexagonal.port.out.CustomerPersistencePort;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCustomerPersistencePort implements CustomerPersistencePort {
    private ConcurrentHashMap<String, Customer> customers = new ConcurrentHashMap<>();

    @Override
    public Customer save(Customer customer) {
        customers.put(customer.getUsername(), customer);
        return customer;
    }

    @Override
    public Customer getByUsername(String username) {
        return customers.get(username);
    }
}
