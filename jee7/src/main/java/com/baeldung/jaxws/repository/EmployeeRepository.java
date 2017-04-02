package com.baeldung.jaxws.repository;

import com.baeldung.jaxws.exception.EmployeeAlreadyExists;
import com.baeldung.jaxws.exception.EmployeeNotFound;
import com.baeldung.jaxws.model.Employee;

import java.util.List;

public interface EmployeeRepository {

    List<Employee> getAllEmployees();

    Employee getEmployee(int id) throws EmployeeNotFound;

    Employee updateEmployee(int id, String name) throws EmployeeNotFound;

    boolean deleteEmployee(int id) throws EmployeeNotFound;

    Employee addEmployee(int id, String name) throws EmployeeAlreadyExists;

    int count();
}
