package com.baeldung.webflux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/employees")
public class EmployeeOfTheSecondController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value="/current", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<Employee> getEmployeeOfTheSecond() {
        return employeeService.getEmployeeOfTheSecond();
    }

}
