package com.baeldung.patterns.hexagonal.controller;

import com.baeldung.patterns.hexagonal.domain.Employee;
import com.baeldung.patterns.hexagonal.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ArrayList<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public void addEmployee(Employee e) {
        employeeService.addEmployee(e);
    }

    @GetMapping("/{id}")
    public Employee getEmployeesById(@PathVariable(value = "id") Integer id) {
        return employeeService.getEmployeesById(id);
    }

}
