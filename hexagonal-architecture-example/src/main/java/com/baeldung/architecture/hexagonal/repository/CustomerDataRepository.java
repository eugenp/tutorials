package com.baeldung.architecture.hexagonal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.architecture.hexagonal.entity.Customer;

public interface CustomerDataRepository extends JpaRepository<Customer, Long> {

}
