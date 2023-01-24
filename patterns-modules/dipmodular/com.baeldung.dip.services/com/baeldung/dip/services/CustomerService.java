package com.baeldung.dip.services;

import com.baeldung.dip.daos.CustomerDao;
import com.baeldung.dip.entities.Customer;
import java.util.Map;
import java.util.Optional;

public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Optional<Customer> findById(int id) {
        return customerDao.findById(id);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }
}
