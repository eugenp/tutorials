package com.baeldung.pattern.hexagonal.controller;

import com.baeldung.pattern.hexagonal.domain.model.Employee;
import com.baeldung.pattern.hexagonal.domain.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping(path = "/{employeeId}")
    public Employee getEmployee(@PathVariable("employeeId") String employeeId) {
        return employeeService.getEmployee(employeeId);
    }
}
