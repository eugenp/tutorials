package com.baeldung.spring-boot-persistence.tomcatconnectionpool.application.repositories;

import com.baeldung.spring-boot-persistence.tomcatconnectionpool.application.entities.Customer;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    
    List<Customer> findByLastName(String lastName);
}
