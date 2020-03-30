package com.baeldung.boot.hexagonal.architecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.boot.hexagonal.architecture.domain.Customer;
import com.baeldung.boot.hexagonal.architecture.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/create")
    public Customer CreateCustomer(@RequestBody Customer customer) {
        log.info("Request arrived for saving the customer.");
        Customer createdCustomer = customerService.createCustomer(customer);
        log.info("Customer added successfully.");
        return createdCustomer;
    }

    @GetMapping("/{customerId}/details")
    public Customer getCustomer(@PathVariable Long customerId) {
        return customerService.fetchCustomerById(customerId);
    }
}
