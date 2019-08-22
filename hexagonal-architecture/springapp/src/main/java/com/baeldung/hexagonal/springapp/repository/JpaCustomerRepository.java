package com.baeldung.hexagonal.springapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.hexagonal.core.Customer;
import com.baeldung.hexagonal.springapp.entity.CustomerEntity;
import com.baeldung.hexagonal.springapp.mapper.CustomerMapper;

public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {

    default Optional<Customer> getCustomer(String email) {
        return findCustomerByEmail(email).map(CustomerMapper.INSTANCE::toModel);
    }

    Optional<CustomerEntity> findCustomerByEmail(String email);

}
