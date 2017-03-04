package com.baeldung.jaxws.repository;

import com.baeldung.jaxws.model.Employee;

import java.util.List;

public interface EmployeeRepository {

    List<Employee> getAllEmployees();

    Employee getEmployee(int id);

    Employee updateEmployee(int id, String name);

    boolean deleteEmployee(int id);

    Employee addEmployee(int id, String name);

    int count();
}
