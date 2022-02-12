package com.baeldung.pattern.hexagonal.domain.services;

import com.baeldung.pattern.hexagonal.domain.model.Address;
import com.baeldung.pattern.hexagonal.domain.model.Customer;
import com.baeldung.pattern.hexagonal.persistence.CustomerRepository;
import com.baeldung.pattern.hexagonal.persistence.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private AddressRepository addressRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.add(customer);
    }

    @Override
    public Customer getCustomer(String userId) {
        Optional<Customer> customer = customerRepository.findByUserId(userId);

        if (customer.isPresent()) {
            return customer.get();
        } else {
            // throw
        }
        return null;
    }

	@Override
	public Address addAddress(Address address) {
		return addressRepository.save(address);
	}

	@Override
	public void updateAddress(Address address) {
		 addressRepository.save(address);
		
	}

	@Override
	public Address getAddress(String addressId) {
		Optional<Address> address = addressRepository.findByAddressId(addressId);

        if (address.isPresent()) {
            return address.get();
        } else {
            // throw
        }
        return null;
	}
}
