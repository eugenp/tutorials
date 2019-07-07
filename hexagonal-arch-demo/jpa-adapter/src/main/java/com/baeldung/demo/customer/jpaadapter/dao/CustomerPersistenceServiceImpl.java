package com.baeldung.demo.customer.jpaadapter.dao;

import com.baeldung.demo.customer.jpaadapter.repository.CustomerRepository;
import com.baeldung.demo.model.Customer;
import com.baeldung.demo.port.secondary.CustomerPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerPersistenceServiceImpl implements CustomerPersistenceService {
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public void createCustomer(Customer customer) {
        customerRepository;
    }

    @Override
    public Customer retrieveCustomer(String customerId) {
        return null;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }
    //Implemetation
}
