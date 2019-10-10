package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.model.Employee;

import java.util.List;

public interface EmployeeService {
    void createEmployee(Employee employee);
    List<Employee> getEmployeeList();
}
