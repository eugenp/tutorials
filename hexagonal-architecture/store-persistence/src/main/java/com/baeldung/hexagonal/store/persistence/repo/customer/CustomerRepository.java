package com.baeldung.hexagonal.store.persistence.repo.customer;

import com.baeldung.hexagonal.store.core.context.customer.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}