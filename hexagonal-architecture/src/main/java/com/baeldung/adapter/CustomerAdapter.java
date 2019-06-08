package com.baeldung.adapter;

import com.baeldung.domain.entity.Customer;
import com.baeldung.domain.port.CustomerPort;

public class CustomerAdapter implements CustomerPort {
    private CustomerRepository customerRepository;

    public CustomerAdapter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getCustomerById(long id) {
        return customerRepository.findById(id);
    }
}
