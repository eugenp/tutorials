package com.baeldung.adapter;

import com.baeldung.domain.entity.Customer;
import com.baeldung.domain.port.CustomerRepositoryPort;

public class CustomerRepositoryAdapter implements CustomerRepositoryPort {
    private CustomerRepository customerRepository;

    public CustomerRepositoryAdapter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getCustomer() {
        return customerRepository.findById();
    }
}
