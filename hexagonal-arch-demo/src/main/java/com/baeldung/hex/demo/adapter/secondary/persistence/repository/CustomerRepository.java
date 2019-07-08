package com.baeldung.hex.demo.adapter.secondary.persistence.repository;

import com.baeldung.hex.demo.adapter.secondary.persistence.entity.CustomerEntity;
import com.baeldung.hex.demo.core.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
}
