package com.baeldung.boot.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository empRepository;

    @GetMapping("/employee/{empId}")
    public Employee get(@PathVariable(name = "empId") Integer empId) {
        return empRepository.findById(empId).get();
    }

    @PostMapping("/employee")
    public Employee createUser(@RequestBody Employee employee) {
        System.out.println(employee);
        empRepository.save(employee);
        return employee;
    }
}
