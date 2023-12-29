package com.baeldung.jsonignore.controller;

import com.baeldung.jsonignore.nullfields.Employee;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EmployeeEchoController.USERS)
public class EmployeeEchoController {

    public static final String USERS = "/employees";

    @PostMapping
    public Employee echoUser(@RequestBody Employee employee) {
        return employee;
    }
}
