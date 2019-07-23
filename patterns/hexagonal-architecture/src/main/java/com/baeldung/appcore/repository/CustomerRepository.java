package com.baeldung.appcore.repository;

import java.util.List;
import java.util.Optional;

import com.baeldung.appcore.domain.Customer;

public interface CustomerRepository {
    Customer registerCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Optional<Customer> findCustomerById(int customerId);
}