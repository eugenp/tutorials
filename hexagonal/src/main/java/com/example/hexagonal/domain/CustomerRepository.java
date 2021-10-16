package com.example.hexagonal.domain;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long>{	
	Customer findCustomerByUserid(long id);
}
