package com.baeldung.nishit.hexagonal.domain.service;

import com.baeldung.nishit.hexagonal.application.port.inbound.EmployeeService;
import com.baeldung.nishit.hexagonal.application.port.outbound.EmployeeRepository;
import com.baeldung.nishit.hexagonal.domain.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee addEmployee(String name, int age) {
        Employee emp = Employee.builder().id(UUID.randomUUID().toString())
                .name(name)
                .age(age)
                .build();
        return employeeRepository.addEmployee(emp);
    }

    @Override
    public Employee getEmployee(String id) {
        return employeeRepository.getEmployee(id);
    }

    @Override
    public Employee deleteEmployee(String id) {
        return employeeRepository.deleteEmployee(id);
    }

}
