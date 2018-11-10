package com.baeldung.hexagonal.repository;

import java.util.List;
import java.util.Optional;

import com.baeldung.hexagonal.domain.Customer;
import com.baeldung.hexagonal.exception.CustomerNotFoundException;

public interface CustomerRepository {

    public Customer CreateCustomer(Customer customer);

    public Customer UpdateCustomer(Customer customer) throws CustomerNotFoundException;

    public List<Customer> findAll();

    public Optional<Customer> findCustomerById(int customerId);

}
