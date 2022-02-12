package com.baeldung.pattern.hexagonal.domain.services;

import com.baeldung.pattern.hexagonal.domain.model.Customer;
import com.baeldung.pattern.hexagonal.domain.model.Address;

public interface CustomerService {

	Customer addCustomer(Customer customer);

	Customer getCustomer(String userId);
	
	Address addAddress(Address address);
	
	void updateAddress(Address address);
	
	Address getAddress(String addressId);
}
