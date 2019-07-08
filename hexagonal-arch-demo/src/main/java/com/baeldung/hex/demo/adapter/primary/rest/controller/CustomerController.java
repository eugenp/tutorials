package com.baeldung.hex.demo.adapter.primary.rest.controller;


import com.baeldung.hex.demo.adapter.primary.rest.resource.CustomerResource;
import com.baeldung.hex.demo.adapter.primary.rest.translator.CustomerAdapter;
import com.baeldung.hex.demo.core.model.Customer;
import com.baeldung.hex.demo.port.primary.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerAdapter customerAdapter;
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResource> getCustomerById(@PathVariable String customerId) {
        return ResponseEntity.ok().body(customerAdapter.toResource(customerService.getCustomer(customerId)));
    }
    @PutMapping
    public ResponseEntity<CustomerResource> updateCustomerAddress(Customer customer) {
        return ResponseEntity.ok().body(customerAdapter.toResource(customerService.updateCustomerAddress(customer)));
    }
    @PostMapping
    public ResponseEntity<CustomerResource> createCustomer(CustomerResource customerResource) {
        Customer customerDomain = customerAdapter.toDomain(customerResource);
        return ResponseEntity.ok().body(customerAdapter.toResource(customerService.createCustomer(customerDomain)));
    }
    @GetMapping
    public ResponseEntity<Boolean> isCustomerEligibleForUpgrade(@PathVariable String customerId) {
        return ResponseEntity.ok().body(customerService.isFreeShippingEligible(customerId));
    }
}
