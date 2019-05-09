package com.baeldung.hexagonal.core;


import com.baeldung.hexagonal.secondary.CustomerRepositoryPort;


public class CustomerService {
    private CustomerRepositoryPort customerRepository;

    public CustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepository = customerRepositoryPort;
    }

    public void addCustomer(Customer customer) {
        customerRepository.saveCustomer(customer);
    }

    public void deleteCustomer(int customerId) {
        customerRepository.deleteCustomer(customerId);
    }

    public Customer getCustomer(int customerId) {
         return customerRepository.findCustomer(customerId);
    }

    public void editCustomer(Customer customer) {
        customerRepository.updateCustomer(customer);
    }
}
