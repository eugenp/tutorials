package com.baeldung.boot.hexagonal.architecture.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.baeldung.boot.hexagonal.architecture.dao.CustomerDao;
import com.baeldung.boot.hexagonal.architecture.domain.Customer;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class CustomerDaoImpl implements CustomerDao {

    private List<Customer> customers = new ArrayList<>();

    @Override
    public void createCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public Customer fetchCustomerById(Long customerId) {
        log.info("Fetching customer with id: {}", customerId);
        if (!customers.isEmpty()) {
            Optional<Customer> customerOptional = customers.stream()
                .filter(customer -> {
                    return customer.getCustomerId()
                        .longValue() == customerId.longValue();
                })
                .findFirst();
            if (customerOptional.isPresent()) {
                log.info("Customer found with following details: {}", customerOptional.get());
                return customerOptional.get();
            }
        }
        log.info("No customer found.");
        return null;
    }
}
