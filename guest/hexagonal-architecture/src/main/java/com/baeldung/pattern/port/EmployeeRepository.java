package com.baeldung.pattern.port;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.baeldung.pattern.domain.Employee;

@Repository
public interface EmployeeRepository {

    Optional<Employee> findById(String id);

}
