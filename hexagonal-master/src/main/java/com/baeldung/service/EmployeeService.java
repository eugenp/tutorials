package com.baeldung.service;

import com.baeldung.domain.Employee;
import com.baeldung.port.EmployeeRepositoryPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepositoryPort employeeRepository;

    public void create(String name, String role, long salary){
        employeeRepository.create(name, role, salary);
    }

    public Employee view(Integer userId){
        return employeeRepository.getEmployee(userId);
    }
}