package com.baeldung.hexagonal.store.core.context.customer.infrastructure;

import com.baeldung.hexagonal.store.core.context.customer.entity.Customer;

import java.util.Optional;

public interface CustomerDataStore {
    Customer save(Customer customer);

    Optional<Customer> findById(Long customerId);

    Iterable<Customer> findAll();
}
