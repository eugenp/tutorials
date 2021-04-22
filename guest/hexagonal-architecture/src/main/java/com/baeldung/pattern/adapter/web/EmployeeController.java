package com.baeldung.pattern.adapter.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.pattern.domain.Employee;
import com.baeldung.pattern.port.EmployeeService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable String id) {
        return employeeService.getEmployee(id);
    }
}
