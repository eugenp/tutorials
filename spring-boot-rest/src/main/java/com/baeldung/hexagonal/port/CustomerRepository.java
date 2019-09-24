package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByName(String name);
}
