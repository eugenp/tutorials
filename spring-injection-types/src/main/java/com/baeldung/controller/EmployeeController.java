package com.baeldung.controller;

import com.baeldung.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/greet/employee" , method= RequestMethod.GET)
    public String greetEmployees(){
       employeeService.greetEmployees();
       return "Employees are Greeted!";
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }
}
