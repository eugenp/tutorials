package com.baeldung.architecture.adapter.rest;

import com.baeldung.architecture.core.domain.Customer;
import com.baeldung.architecture.port.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    void create(@RequestBody Customer customer) {

    }

    @GetMapping("/{name}")
    public Customer getByName(@PathVariable String name) {
        return customerService.getByName(name);
    }

    @GetMapping
    public List<Customer> list() {
        return customerService.getAll();
    }
}
