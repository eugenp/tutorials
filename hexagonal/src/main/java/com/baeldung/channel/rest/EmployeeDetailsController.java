package com.baeldung.channel.rest;

import java.util.Optional;

import com.baeldung.domain.Employee;
import com.baeldung.port.EmployeeDetailsPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees/")
public class EmployeeDetailsController{

    @Autowired
    private EmployeeDetailsPort employeeDetailsPort;

    @PostMapping("create")
    public CreateEmployeeResponse createUser(@RequestBody CreateEmployeeRequest request) {
        Employee newUser = employeeDetailsPort.create(request.getName(), request.getRole(), request.getSalary());
        return new CreateEmployeeResponse(newUser.getId(), newUser.getName());
    }

    @GetMapping("view/{id}")
    public ViewEmployeeResponse viewUser(@PathVariable Integer id) {
        Optional<Employee> employee = employeeDetailsPort.getEmployee(id);
        return employee.map(emp -> new ViewEmployeeResponse(emp.getId(), emp.getName(), emp.getRole(), emp.getSalary()))
          .orElseGet(ViewEmployeeResponse::new);
    }
}