package com.baeldung.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements EmployeeServicePort {

    private EmployeeRepositoryPort employeeRepositoryPort;

    @Autowired
    public EmployeeService(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepositoryPort.findAll();
    }

    @Override
    public Employee get(String employeeId) {
        return employeeRepositoryPort.findById(employeeId)
            .get();
    }

    @Override
    public Employee create(Employee employee) {
        return employeeRepositoryPort.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeRepositoryPort.save(employee);
    }
}
