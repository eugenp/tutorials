package com.baeldung.architecture.hexagonal.repository;

import java.util.List;

import com.baeldung.architecture.hexagonal.entity.Customer;

public interface CustomerRepository {

	void create(String name, String email);

	List<Customer> getCustomers();

	Customer getCustomer(Long id);

}
