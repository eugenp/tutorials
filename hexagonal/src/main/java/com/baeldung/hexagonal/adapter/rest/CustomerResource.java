package com.baeldung.hexagonal.adapter.rest;

import com.baeldung.hexagonal.domain.model.Customer;
import com.baeldung.hexagonal.port.in.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerResource {
    private final CustomerService customerService;

    @PostMapping("/customer")
    public ResponseEntity<Customer> registerCustomer(
            @RequestBody Customer customer) {
        return ResponseEntity
                .ok()
                .body(customerService.register(customer));
    }

    @GetMapping("/customer/{username}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String username) {
        return ResponseEntity
                .ok()
                .body(customerService.getByUsername(username));
    }
}
