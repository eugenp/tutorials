package com.baeldung.books.repositories;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.books.models.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {

}
