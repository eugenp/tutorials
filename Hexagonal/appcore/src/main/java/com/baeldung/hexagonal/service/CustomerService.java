package com.baeldung.hexagonal.service;

import java.util.List;
import java.util.Optional;

import com.baeldung.hexagonal.domain.Customer;
import com.baeldung.hexagonal.exception.CustomerNotFoundException;

public interface CustomerService {

    public Customer registerCustomer(Customer customer);

    public Customer upgradeCustomer(Customer customer) throws CustomerNotFoundException;

    public Customer downgradeCustomer(Customer customer) throws CustomerNotFoundException;

    public List<Customer> getAllCustomers();

    public Optional<Customer> findCustomerById(int customerId);

}
