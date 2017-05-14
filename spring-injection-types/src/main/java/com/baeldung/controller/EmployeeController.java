package com.baeldung.controller;

import com.baeldung.model.Employee;
import com.baeldung.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(value = "/employee" , method= RequestMethod.GET)
    public Employee getEmployee(){
       return employeeService.getEmployee();
    }

    @RequestMapping(value= "/employee" , method=RequestMethod.POST)
    public String saveEmployee(@RequestBody Employee employee){
        employeeService.saveEmployee(employee);
        return String.format("Employee %s %s saved Successfully!", employee.getFirstName() , employee.getLastName());
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }
}
