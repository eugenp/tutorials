package com.baeldung.batchinserts.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.batchinserts.model.Customer;

/**
 * JPA CrudRepository interface
 * 
 * @author ysharma2512
 *
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>{

}
