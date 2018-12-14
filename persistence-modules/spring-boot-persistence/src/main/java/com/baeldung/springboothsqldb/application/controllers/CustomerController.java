package com.baeldung.hsqldb.application.controllers;

import com.baeldung.hsqldb.application.entities.Customer;
import com.baeldung.hsqldb.application.repositories.CustomerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    
    private final CustomerRepository customerRepository;
    
    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @PostMapping("/customer")
    @ResponseBody
    public Customer addCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);
        return customer;
    }
    
    @GetMapping(value = "/customers")
    @ResponseBody
    public List<Customer> getCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }
}
