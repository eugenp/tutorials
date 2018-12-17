package com.baeldung.adapter;
import java.util.Optional;

import com.baeldung.domain.Employee;
import com.baeldung.port.EmployeeDetailsPort;
import com.baeldung.port.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceAdapter implements EmployeeDetailsPort {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(String name, String role, long salary) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setRole(role);
        employee.setSalary(salary);
       
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> getEmployee(Integer userId) {
        return employeeRepository.findById(userId);
    }
}