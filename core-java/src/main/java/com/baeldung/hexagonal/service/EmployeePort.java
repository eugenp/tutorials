package com.baeldung.hexagonal.service;

import java.util.List;

import com.baeldung.hexagonal.entity.Employee;

public interface EmployeePort {

    List<Employee> getEmployees();
}
