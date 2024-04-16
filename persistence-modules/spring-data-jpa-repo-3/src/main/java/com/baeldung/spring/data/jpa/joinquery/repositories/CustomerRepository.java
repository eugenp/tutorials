package com.baeldung.spring.data.jpa.joinquery.repositories;

import com.baeldung.spring.data.jpa.joinquery.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
