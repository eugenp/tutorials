package com.baeldung.pattern.hexagonal.persistence;

import com.baeldung.pattern.hexagonal.domain.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository {

    Customer add(Customer customer);

    Optional<Customer> findByUserId(String userId);

}
