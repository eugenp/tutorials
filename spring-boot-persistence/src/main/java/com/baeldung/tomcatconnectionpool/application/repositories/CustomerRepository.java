package com.baeldung.tomcatconnectionpool.application.repositories;

import com.baeldung.application.entities.Customer;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    
    List<Customer> findByLastName(String lastName);
}
