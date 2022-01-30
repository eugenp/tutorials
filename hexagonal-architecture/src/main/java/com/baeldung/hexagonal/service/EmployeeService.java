package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.Employee;

public interface EmployeeService {
    Employee createEmployee(Employee employee);

    Employee findEmployeeById(Long id);
}
