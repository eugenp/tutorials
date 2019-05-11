package edu.baeldung.hexagonalarchitecture.core;

import edu.baeldung.hexagonalarchitecture.pojo.Customer;

import java.util.List;
import java.util.Optional;

public interface App {

    public Customer createCustomer(Customer customer);
    public List<Customer> findAll();
    public Optional<Customer> findCustomerById(String customerId);

}
