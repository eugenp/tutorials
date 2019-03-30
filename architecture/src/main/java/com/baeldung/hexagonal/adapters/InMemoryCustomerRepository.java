package com.baeldung.hexagonal.adapters;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.hexagonal.core.Customer;
import com.baeldung.hexagonal.ports.CustomerRepository;

public class InMemoryCustomerRepository implements CustomerRepository {
    
    private static Map<Long, Customer> customers = new HashMap<>();
    
    static {
        customers.put(1L, new Customer(1L, "John"));
        customers.put(2L, new Customer(2L, "Joe"));
        customers.put(3L, new Customer(3L, "Jack"));
    }

    @Override
    public Customer findBy(Long id) {
        return customers.get(id);
    }

}
