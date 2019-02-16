package com.baeldung.dao.repositories;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.domain.Customer;

/**
 * JPA CrudRepository interface
 * 
 * @author ysharma2512
 *
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>{

}
