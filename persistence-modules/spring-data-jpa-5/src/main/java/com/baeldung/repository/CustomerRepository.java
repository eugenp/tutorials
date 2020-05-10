package com.baeldung.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Cacheable("customers")
    Customer findById(long id);
}