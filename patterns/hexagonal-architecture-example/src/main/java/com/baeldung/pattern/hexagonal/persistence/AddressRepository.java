package com.baeldung.pattern.hexagonal.persistence;

import java.util.Optional;

import com.baeldung.pattern.hexagonal.domain.model.Address;

public interface AddressRepository {

	 Optional<Address> findByAddressId(String addressId);
	 
	 Address save(Address address);
}
