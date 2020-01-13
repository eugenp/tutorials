package com.baeldung.hexagonalarchitecturejava.service;

import com.baeldung.hexagonalarchitecturejava.domain.Employee;

import java.util.List;

public interface IEmployeeService
{
    List<Employee> getAllEmployee();
    Employee save(Employee employee);
    Employee update(Employee employee) throws Exception;
    Employee findEmployeeById(Long employeeId);
    void deleteById(Long employeeId);
}
