package com.baeldung.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.jpa.entity.Customer;

/**
 * JPA CrudRepository interface
 * 
 * @author ysharma2512
 *
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>{

}
