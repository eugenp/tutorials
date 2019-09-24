package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.Customer;
import com.baeldung.hexagonal.port.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void create(String name, String companyName) {
        Customer customer = new Customer(name, companyName);
        customerRepository.save(customer);
    }

    public Customer find(String name) {
        return customerRepository.findByName(name);
    }
}
