package com.baeldung.hexagonal;

import com.baeldung.hexagonal.model.Employee;

import java.util.List;


public interface InboundPort {

    void createEmployee(Employee employee);
    List<Employee> getEmployeeList();
}
