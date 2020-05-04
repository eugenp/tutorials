package com.baeldung.architecture.hexagonal.service;

import java.util.List;

import com.baeldung.architecture.hexagonal.entity.Customer;

public interface CustomerService {

	void createCustomer(String name, String email);

	List<Customer> getCustomers();

	Customer getCustomer(Long id);

}
