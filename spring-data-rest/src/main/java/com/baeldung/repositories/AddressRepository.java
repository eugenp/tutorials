package com.baeldung.repositories;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.models.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {

}
