package com.baeldung.architecture.hexagonal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.architecture.hexagonal.entity.Customer;
import com.baeldung.architecture.hexagonal.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/customers")
	public void addUser(@RequestBody Customer customer) {
		customerService.createCustomer(customer.getName(), customer.getEmail());
	}

	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		return customerService.getCustomers();
	}

	@GetMapping("/customer/{id}")

	public Customer getCustomer(@PathVariable Long id) {
		return customerService.getCustomer(id);
	}
}
