package com.baeldung.boot.hexagonal.architecture.dao;

import com.baeldung.boot.hexagonal.architecture.domain.Customer;

public interface CustomerDao {

    void createCustomer(Customer customer);

    Customer fetchCustomerById(Long customerId);
}
