package com.baeldung.hexagonal.adapter.persistence.repositories;

import com.baeldung.hexagonal.adapter.persistence.model.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JpaCustomerRepository extends CrudRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByUsername(String username);
}
