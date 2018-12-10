package com.baeldung.springboothsqldb.application.repositories;

import com.baeldung.springboothsqldb.application.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {}
