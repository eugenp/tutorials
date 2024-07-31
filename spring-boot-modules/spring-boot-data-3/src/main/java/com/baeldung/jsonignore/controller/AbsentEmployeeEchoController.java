package com.baeldung.jsonignore.controller;

import com.baeldung.jsonignore.absentfields.Employee;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AbsentEmployeeEchoController.USERS)
public class AbsentEmployeeEchoController {

    public static final String USERS = "/absent_employees";

    @PostMapping
    public Employee echoUser(@RequestBody Employee employee) {
        return employee;
    }
}
