package com.baeldung.reactive.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @GetMapping("/{id}")
    private Mono<Employee> getEmployeeById(@PathVariable String id)
    {
        return employeeRepository.findEmployeeById(id);
    }
    
    @GetMapping
    private Flux<Employee> getAllEmployees()
    {
        return employeeRepository.findAllEmployees();
    }

    @GetMapping("/access-key/{id}")
    private Mono<String> getEmployeeAccessKey(@PathVariable String id)
    {
        return employeeRepository.findEmployeeAccessKey(id);
    }

}
