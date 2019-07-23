package com.baeldung.appcore.service;

import java.util.List;
import java.util.Optional;

import com.baeldung.appcore.domain.Customer;
import com.baeldung.appcore.repository.CustomerRepository;


public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public Customer registerCustomer(Customer customer) {
        return customerRepository.registerCustomer(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.updateCustomer(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }


    @Override
    public Optional<Customer> findCustomerById(int customerId) {
        return customerRepository.findCustomerById(customerId);
    }

}