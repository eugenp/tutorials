package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.port.EmployeeRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepositoryPort employeeRepository;

    public boolean create(String name, String role, long salary){
        return employeeRepository.create(name, role, salary);
    }

    public Employee view(Integer userId){
        return employeeRepository.getEmployee(userId);
    }
}