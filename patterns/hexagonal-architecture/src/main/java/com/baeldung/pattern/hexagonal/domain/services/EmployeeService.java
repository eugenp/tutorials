package com.baeldung.pattern.hexagonal.domain.services;

import com.baeldung.pattern.hexagonal.domain.model.Employee;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    Employee getEmployee(String employeeId);
}
