package com.baeldung.demo.customer.jpaadapter.dao;

import com.baeldung.demo.customer.jpaadapter.repository.CustomerRepository;
import com.baeldung.demo.model.Customer;
import com.baeldung.demo.port.secondary.CustomerPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerPersistenceServiceImpl implements CustomerPersistenceService {
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer retrieveCustomer(String customerId) {
        return customerRepository.getOne(customerId);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    //Implemetation
}
