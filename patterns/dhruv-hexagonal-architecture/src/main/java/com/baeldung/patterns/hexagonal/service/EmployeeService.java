package com.baeldung.patterns.hexagonal.service;

import java.util.ArrayList;

import com.baeldung.patterns.hexagonal.domain.Employee;

public interface EmployeeService {
    ArrayList<Employee> getAllEmployees();

    void addEmployee(Employee e);

    Employee getEmployeesById(int id);
}
