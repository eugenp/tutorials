package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.port.EmployeeControllerPort;
import com.baeldung.hexagonal.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees/")
public class EmployeeControllerAdapter implements EmployeeControllerPort {

    @Autowired
    private EmployeeService employeeService;

   @Override
    public boolean create(@RequestBody Employee request) {
        return employeeService.create(request.getName(), request.getRole(), request.getSalary());
    }

    @Override
    public Employee view(@PathVariable Integer id) {
        return employeeService.view(id);
    }
}