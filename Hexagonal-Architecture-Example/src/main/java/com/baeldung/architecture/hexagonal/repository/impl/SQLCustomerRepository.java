package com.baeldung.architecture.hexagonal.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.architecture.hexagonal.entity.Customer;
import com.baeldung.architecture.hexagonal.repository.CustomerDataRepository;
import com.baeldung.architecture.hexagonal.repository.CustomerRepository;

@Component
public class SQLCustomerRepository implements CustomerRepository {

	@Autowired
	private CustomerDataRepository customerRepository;

	@Override
	public void create(String name, String email) {
		customerRepository.save(new Customer(name, email));
	}

	@Override
	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Customer getCustomer(Long id) {
		return customerRepository.getOne(id);
	}

}
