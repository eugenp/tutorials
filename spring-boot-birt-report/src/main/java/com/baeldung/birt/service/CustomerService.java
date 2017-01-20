package com.baeldung.birt.service;

import com.baeldung.birt.domain.Customer;
import com.baeldung.birt.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dsharew on 1/14/17.
 */
@Service
public class CustomerService {

    CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;

    }

    public void createCustomer(Customer customer) {

        customerRepository.save(customer);

    }

}
