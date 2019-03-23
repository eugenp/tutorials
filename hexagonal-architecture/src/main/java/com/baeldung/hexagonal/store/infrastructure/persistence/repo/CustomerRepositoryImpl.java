package com.baeldung.hexagonal.store.infrastructure.persistence.repo;

import com.baeldung.hexagonal.store.core.context.customer.entity.Customer;
import com.baeldung.hexagonal.store.core.context.customer.infrastructure.CustomerDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
public class CustomerRepositoryImpl implements CustomerDataStore {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }
}

@Repository
interface CustomerRepository extends CrudRepository<Customer, Long> {
}