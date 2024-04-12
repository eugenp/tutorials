package com.baeldung.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.core.client.EmployeeClient;
import com.baeldung.core.model.Employee;

import feign.Feign;
import feign.form.spring.SpringFormEncoder;

@RestController
public class EmployeeController {

    private static final String HTTP_FILE_EMPLOYEE_URL = "http://localhost:8081";

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@RequestParam("id") long id) {
        EmployeeClient employeeResource = Feign.builder().encoder(new SpringFormEncoder())
                .target(EmployeeClient.class, HTTP_FILE_EMPLOYEE_URL);
        return employeeResource.getEmployee(id, true);
    }
}
