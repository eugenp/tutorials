package com.baeldung.demo.customer.jpaadapter.repository;

import com.baeldung.demo.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
