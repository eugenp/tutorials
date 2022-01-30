package com.baeldung.hexagonal.controller;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/{id}")
    public Employee findEmployee(@PathVariable String id) {
        return employeeService.findEmployeeById(Long.parseLong(id));
    }

    @PostMapping
    public void createAccount(@RequestBody Employee employee) {
        employeeService.createEmployee(employee);
    }
}
