package com.baeldung.patterns.hexagonal.dao;

import java.util.ArrayList;

import com.baeldung.patterns.hexagonal.domain.Employee;

public interface EmployeeDao {
    ArrayList<Employee> getAllEmployees();

    void addEmployee(Employee e);

    Employee getEmployeesById(int id);
}
