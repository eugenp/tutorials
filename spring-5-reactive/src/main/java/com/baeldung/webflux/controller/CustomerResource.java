package com.baeldung.webflux.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import com.baeldung.webflux.model.Customer;
import com.baeldung.webflux.service.CustomerService;


@RestController
@RequestMapping("/api")
public class CustomerResource {
    private final CustomerService customerService;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/api/customers", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Customer> getCustomers() {
        return customerService.findAll().log();
    }

}
