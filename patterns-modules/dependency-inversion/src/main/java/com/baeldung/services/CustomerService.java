package com.baeldung.services;

import com.baeldung.daointerfaces.CustomerDao;
import com.baeldung.entities.Customer;
import java.util.List;
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
