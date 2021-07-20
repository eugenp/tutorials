package com.baeldung.architecture.port;

import com.baeldung.architecture.core.domain.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> getAll();

    void create(Customer customer);

    Customer getByName(String name);
}
