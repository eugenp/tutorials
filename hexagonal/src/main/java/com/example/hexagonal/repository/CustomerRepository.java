package com.example.hexagonal.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.hexagonal.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findCustomerByUserid(long id);
}
