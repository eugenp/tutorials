package com.baeldung.dipmodular.services;

import com.baeldung.dipmodular.daos.CustomerDao;
import com.baeldung.dipmodular.entities.Customer;
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
