package com.baeldung.architecture.hexagonal.service;

import com.baeldung.architecture.hexagonal.model.Employee;

import java.util.List;

public interface EmployeeService {
    void createEmployee(Employee employee);
    List<Employee> getEmployeeList();
}
