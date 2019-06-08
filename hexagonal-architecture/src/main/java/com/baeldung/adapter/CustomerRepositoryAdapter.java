package com.baeldung.adapter;

import com.baeldung.domain.entity.Customer;
import com.baeldung.domain.port.CustomerRepositoryPort;

public class CustomerRepositoryAdapter implements CustomerRepositoryPort {
    @Override
    public Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Carlos");

        return customer;
    }
}
