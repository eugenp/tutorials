package com.baeldung.dependencyinjection;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class InMemCustomerRepository implements CustomerRepository {

    @Override
    public List<Customer> findAll() {
        return Arrays.asList(
            new Customer(1L, "customer1"), 
            new Customer(2L, "customer2"), 
            new Customer(3L, "customer3"));
    }
}
