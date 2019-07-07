package com.baeldung.demo.customer.restadapter.controller;

import com.baeldung.demo.model.Customer;
import com.baeldung.demo.port.primary.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String customerId) {
        return ResponseEntity.ok().body(customerService.getCustomer(customerId));
    }
    @PutMapping
    public ResponseEntity<Customer> updateCustomerAddress(Customer customer) {
        return ResponseEntity.ok().body(customerService.updateCustomerAddress(customer));
    }
    @GetMapping
    public ResponseEntity<Boolean> isCustomerEligibleForUpgrade(@PathVariable String customerId) {
        return ResponseEntity.ok().body(customerService.isCustomerEligibleForUpgrade(customerId));
    }
}
