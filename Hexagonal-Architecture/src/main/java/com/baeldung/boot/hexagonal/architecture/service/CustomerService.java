package com.baeldung.boot.hexagonal.architecture.service;

import com.baeldung.boot.hexagonal.architecture.domain.Customer;

public interface CustomerService {
	public Customer createCustomer(Customer customer);
	public Customer fetchCustomerById(Long customerId);
}
