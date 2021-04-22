package com.baeldung.pattern.adapter.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.baeldung.pattern.domain.Employee;
import com.baeldung.pattern.port.EmployeeRepository;

@Repository
public class JPARepositoryAdapter implements EmployeeRepository {

    @Override
    public Optional<Employee> findById(String id) {
        // logic to get employee from jpaRepository
        return Optional.of(new Employee());
    }
}
