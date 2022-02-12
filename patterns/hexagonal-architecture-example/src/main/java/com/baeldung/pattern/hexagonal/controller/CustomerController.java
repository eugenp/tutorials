package com.baeldung.pattern.hexagonal.controller;

import com.baeldung.pattern.hexagonal.domain.model.Customer;
import com.baeldung.pattern.hexagonal.domain.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Customer addCustomer(@RequestBody Customer customer) {
        Customer response =  customerService.addCustomer(customer);
        if (customer.getHomeAddress() != null) {
        	if (customer.getHomeAddress().getUserId() == null)
        	  customer.getHomeAddress().setUserId(customer.getUserId());
        	customerService.addAddress(customer.getHomeAddress());
        }
        return response;
    }

    @GetMapping(path = "/{userId}")
    public Customer getCustomer(@PathVariable("userId") String userId) {
        return customerService.getCustomer(userId);
    }
}
