package com.baeldung.jpa.subtypes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.jpa.subtypes.entity.ContractEmployee;

public interface ContractEmployeeRepository extends JpaRepository<ContractEmployee, Long> {
}
