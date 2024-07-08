package com.baeldung.bulkandbatchapi.service;

import com.baeldung.bulkandbatchapi.request.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    private final Map<String, Customer> customerRepoMap = new HashMap<>();

    public List<Customer> createCustomers(List<Customer> customers) {
        return customers.stream()
                .map(this::createCustomer)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    public Optional<Customer> createCustomer(Customer customer) {
        LOGGER.info("Creating Customer : {}", customer);

        if (!customerRepoMap.containsKey(customer.getEmail()) && customer.getId() == 0) {
            Customer customerToCreate = new Customer(customerRepoMap.size() + 1, customer.getName(), customer.getEmail());
            customerToCreate.setAddress(customer.getAddress());
            customerRepoMap.put(customerToCreate.getEmail(), customerToCreate);
            LOGGER.info("Created Customer : {}", customerToCreate);

            return Optional.of(customerToCreate);
        }

        return Optional.empty();
    }

    public Optional<Customer> updateCustomer(Customer customer) {
        LOGGER.info("Updating Customer : {}", customer);

        Customer customerToUpdate = customerRepoMap.get(customer.getEmail());

        if (customerToUpdate != null && customerToUpdate.getId() == customer.getId()) {
            customerToUpdate.setName(customer.getName());
            customerToUpdate.setAddress(customer.getAddress());

            LOGGER.info("Updated Customer : {}", customerToUpdate);
        }

        return Optional.ofNullable(customerToUpdate);
    }

    public Optional<Customer> deleteCustomer(Customer customer) {
        LOGGER.info("Deleting Customer : {}", customer);
        Customer customerToDelete = customerRepoMap.get(customer.getEmail());

        if (customerToDelete != null && customerToDelete.getId() == customer.getId()) {
            customerRepoMap.remove(customer.getEmail());
            LOGGER.info("Deleted Customer : {}", customerToDelete);
        }

        return Optional.ofNullable(customerToDelete);
    }
}
