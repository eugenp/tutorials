package com.baeldung.domain;

import java.util.List;

import com.baeldung.exception.EmployeeNotFoundException;

public interface EmployeeServicePort {

  List<Employee> getAll();

  Employee get(String employeeId) throws EmployeeNotFoundException;

  Employee create(Employee employee);

  Employee update(Employee employee);
}
