package com.baeldung.boot.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository empRepository;

    @GetMapping("/employee/{empId}")
    public Employee get(@PathVariable(name = "empId") Integer empId) {
        Optional<Employee> emp = empRepository.findById(empId);
        return emp.orElse(null);
    }

    @PostMapping("/employee")
    public Employee createUser(@RequestBody Employee employee) {
        System.out.println(employee);
        empRepository.save(employee);
        return employee;
    }
}
