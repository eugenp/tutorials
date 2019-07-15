package com.example.infrastructure.controller;

import com.example.application.CustomerRequest;
import com.example.domain.CustomerId;
import com.example.domain.ICustomerCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "customer")
public class CustomerController {
    private final ICustomerCreator customerCreator;

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerId save(@RequestBody CustomerRequest customerRequest) {
        return customerCreator.save(customerRequest);
    }
}
