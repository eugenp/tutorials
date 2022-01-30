package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.createEmployee(employee);
    }

    @Override
    public Employee findEmployeeById(Long id) {
        return employeeRepository.findEmployee(id);
    }
}
