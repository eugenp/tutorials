package com.baeldung.boot.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.boot.domain.Customer;

/**
 * JPA CrudRepository interface
 * 
 * @author ysharma2512
 *
 */
@Repository
@Transactional
public interface CustomerRepository extends CrudRepository<Customer, Long>{

}
