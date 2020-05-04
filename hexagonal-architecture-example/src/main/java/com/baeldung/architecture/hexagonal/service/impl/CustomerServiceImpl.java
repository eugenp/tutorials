package com.baeldung.architecture.hexagonal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.architecture.hexagonal.entity.Customer;
import com.baeldung.architecture.hexagonal.repository.CustomerRepository;
import com.baeldung.architecture.hexagonal.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public void createCustomer(String name, String email) {
		customerRepository.create(name, email);
	}

	@Override
	public List<Customer> getCustomers() {
		return customerRepository.getCustomers();
	}

	@Override
	public Customer getCustomer(Long id) {
		return customerRepository.getCustomer(id);
	}

}
