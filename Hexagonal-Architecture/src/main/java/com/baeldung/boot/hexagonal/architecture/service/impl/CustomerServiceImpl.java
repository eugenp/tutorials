package com.baeldung.boot.hexagonal.architecture.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.boot.hexagonal.architecture.dao.CustomerDao;
import com.baeldung.boot.hexagonal.architecture.domain.Customer;
import com.baeldung.boot.hexagonal.architecture.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDao customerDao;

    private Integer customerId = 1;

    @Override
    public Customer createCustomer(Customer customer) {
        customer.setCustomerId(Long.valueOf(String.valueOf(customerId)));
        customerDao.createCustomer(customer);
        customerId++;
        return customer;
    }

    @Override
    public Customer fetchCustomerById(Long customerId) {
        return customerDao.fetchCustomerById(customerId);
    }

}
