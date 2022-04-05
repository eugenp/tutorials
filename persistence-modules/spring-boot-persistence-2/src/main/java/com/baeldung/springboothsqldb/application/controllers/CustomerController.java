package com.baeldung.springboothsqldb.application.controllers;

import com.baeldung.springboothsqldb.application.entities.Customer;
import com.baeldung.springboothsqldb.application.repositories.CustomerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    
    private final CustomerRepository customerRepository;
    
    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);
        return customer;
    }
    
    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }
}
