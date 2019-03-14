package com.baeldung.hexagonal.store.core.context.customer.service;

import com.baeldung.hexagonal.store.core.context.customer.entity.Customer;

public interface CustomerService {
    Iterable<Customer> getAllCustomers();
}
