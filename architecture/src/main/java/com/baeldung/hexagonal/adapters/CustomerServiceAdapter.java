package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.core.Customer;
import com.baeldung.hexagonal.ports.CustomerRepository;
import com.baeldung.hexagonal.ports.CustomerServicePort;

public class CustomerServiceAdapter implements CustomerServicePort {
    
    private CustomerRepository customerRepository;
    
    public CustomerServiceAdapter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findCustomerBy(Long id) {
        Customer customer = this.customerRepository.findBy(id);
        return customer;
    }

}
