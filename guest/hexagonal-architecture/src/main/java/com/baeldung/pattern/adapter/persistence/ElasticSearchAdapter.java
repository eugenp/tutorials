package com.baeldung.pattern.adapter.persistence;

import java.util.Optional;

import com.baeldung.pattern.domain.Employee;
import com.baeldung.pattern.port.EmployeeRepository;

public class ElasticSearchAdapter implements EmployeeRepository {
    @Override
    public Optional<Employee> findById(String id) {
        // logic to get employee data from elastc search
        return Optional.of(new Employee());
    }
}
