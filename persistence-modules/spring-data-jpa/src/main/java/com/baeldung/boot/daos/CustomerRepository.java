package com.baeldung.boot.daos;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.boot.domain.Customer;

/**
 * JPA CrudRepository interface
 * 
 * @author ysharma2512
 *
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>{

}
