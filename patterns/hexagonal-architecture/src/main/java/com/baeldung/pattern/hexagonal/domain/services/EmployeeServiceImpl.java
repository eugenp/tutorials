package com.baeldung.pattern.hexagonal.domain.services;

import com.baeldung.pattern.hexagonal.domain.model.Employee;
import com.baeldung.pattern.hexagonal.persistence.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.add(employee);
    }

    @Override
    public Employee getEmployee(String employeeId) {
        return this.employeeRepository.findById(employeeId).orElse(null);
    }
}
