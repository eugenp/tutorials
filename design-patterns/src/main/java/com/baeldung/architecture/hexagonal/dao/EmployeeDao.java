package com.baeldung.architecture.hexagonal.dao;

import com.baeldung.architecture.hexagonal.model.Employee;

import java.util.List;

public interface EmployeeDao {
    void createEmployee(Employee employee);
    List<Employee> getEmployeeList();
}
