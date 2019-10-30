package com.baeldung.cassandra.reactive.controller;

import com.baeldung.cassandra.reactive.model.Employee;
import com.baeldung.cassandra.reactive.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostConstruct
    public void saveEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(123, "John Doe", "Delaware", "jdoe@xyz.com", 31));
        employees.add(new Employee(324, "Adam Smith", "North Carolina", "asmith@xyz.com", 43));
        employees.add(new Employee(355, "Kevin Dunner", "Virginia", "kdunner@xyz.com", 24));
        employees.add(new Employee(643, "Mike Lauren", "New York", "mlauren@xyz.com", 41));
        employeeService.initializeEmployees(employees);
    }

    @GetMapping("/list")
    public Flux<Employee> getAllEmployees() {
        Flux<Employee> employees = employeeService.getAllEmployees();
        return employees;
    }

    @GetMapping("/{id}")
    public Mono<Employee> getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/filterByAge/{age}")
    public Flux<Employee> getEmployeesFilterByAge(@PathVariable int age) {
        return employeeService.getEmployeesFilterByAge(age);
    }
}
