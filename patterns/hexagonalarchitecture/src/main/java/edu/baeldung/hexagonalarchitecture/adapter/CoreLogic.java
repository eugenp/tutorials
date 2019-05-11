package edu.baeldung.hexagonalarchitecture.adapter;

import edu.baeldung.hexagonalarchitecture.core.App;
import edu.baeldung.hexagonalarchitecture.pojo.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CoreLogic implements App {

    private List<Customer> customerRepository = new ArrayList<>();

    @Override
    public Customer createCustomer(Customer customer) {
        customerRepository.add(customer);
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository;
    }

    @Override
    public Optional<Customer> findCustomerById(String customerId) {
        Optional<Customer> result = customerRepository.stream()
                .filter(customer -> customerId.equalsIgnoreCase(customer.getIdentification()))
                .findFirst();
        return result;
    }
}
