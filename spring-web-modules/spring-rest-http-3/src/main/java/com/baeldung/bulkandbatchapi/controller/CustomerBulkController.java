package com.baeldung.bulkandbatchapi.controller;

import com.baeldung.bulkandbatchapi.request.Customer;
import com.baeldung.bulkandbatchapi.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class CustomerBulkController {

    private final CustomerService customerService;

    public CustomerBulkController(@Autowired CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping(path = "/bulk/customers")
    public List<Customer> bulkCreateCustomers(@RequestBody @Valid @Size(min = 1, max = 20) List<Customer> customers) {
        return customerService.createCustomers(customers);
    }
}
