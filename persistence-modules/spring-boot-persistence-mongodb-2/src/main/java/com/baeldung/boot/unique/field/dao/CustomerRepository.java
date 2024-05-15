package com.baeldung.boot.unique.field.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.boot.unique.field.data.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByStoreIdAndNumber(Long storeId, Long number);
}
