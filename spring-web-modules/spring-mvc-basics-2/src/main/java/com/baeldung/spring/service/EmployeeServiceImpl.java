package com.baeldung.spring.service;

import com.baeldung.spring.domain.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements  EmployeeService{

    @Override
    public List<Employee> getEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(createEmployee(100L, "Steve Martin", "333-777-999"));
        employeeList.add(createEmployee(200L, "Adam Schawn", "444-111-777"));
        return employeeList;
    }

    private Employee createEmployee(long id, String name, String contactNumber) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(name);
        employee.setContactNumber(contactNumber);
        return employee;
    }
}
