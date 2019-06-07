package com.baeldung.adapter;

import com.baeldung.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findById(long id);
}
