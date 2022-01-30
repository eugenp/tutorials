package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.domain.Employee;

public interface EmployeeRepository {
    Employee findEmployee(Long id);

    Employee createEmployee(Employee e);
}