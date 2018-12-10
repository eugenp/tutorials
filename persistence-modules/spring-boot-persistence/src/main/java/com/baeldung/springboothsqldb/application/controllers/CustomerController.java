package com.baeldung.springboothsqldb.application.controllers;

import com.baeldung.springboothsqldb.application.entities.Customer;
import com.baeldung.springboothsqldb.application.repositories.CustomerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @RequestMapping(value = "/addcustomer", method = RequestMethod.POST)
    public Customer addCustomer(
            @RequestParam(value = "name", defaultValue = "Julie") String name,
            @RequestParam(value = "email", defaultValue = "Julie@domain.com") String email) {
        Customer customer = new Customer(name, email);
        customerRepository.save(customer);
        return customer;
    }
    
    @RequestMapping(value = "/getcustomers", method = RequestMethod.GET)
    public List<Customer> getCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }
}
