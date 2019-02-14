package com.baeldung.jpa.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.baeldung.jpa.entity.Customer;
import com.baeldung.jpa.repository.CustomerRepository;

/**
 * A simple controller to test the JPA CrudRepository operations
 * controllers
 * 
 * @author ysharma2512
 *
 */
@Controller
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/customers")
    public BodyBuilder insertCustomers() throws URISyntaxException {
        Customer c1 = new Customer("James", "Gosling");
        Customer c2 = new Customer("Doug", "Lea");
        Customer c3 = new Customer("Martin", "Fowler");
        Customer c4 = new Customer("Brian", "Goetz");
        List<Customer> customers = Arrays.asList(c1, c2, c3, c4);
        customerRepository.saveAll(customers);
        return ResponseEntity.created(new URI("/customers"));
    }

}
