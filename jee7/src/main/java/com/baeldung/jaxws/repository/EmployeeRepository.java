package com.baeldung.jaxws.repository;

import com.baeldung.jaxws.model.Employee;

import java.util.List;

public interface EmployeeRepository {

    List<Employee> getAllEmployees();

    Employee getEmployee(int id);

    Employee updateEmployee(Employee update, int id);

    boolean deleteEmployee(int id);

    Employee addEmployee(Employee employee);

    int count();
}
