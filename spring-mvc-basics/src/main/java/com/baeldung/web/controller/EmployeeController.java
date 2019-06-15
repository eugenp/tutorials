package com.baeldung.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baeldung.model.Employee;

@Controller
public class EmployeeController {

    Map<Long, Employee> employeeMap = new HashMap<>();

    @ModelAttribute("employees")
    public void initEmployees() {
        employeeMap.put(1L, new Employee(1L, "John", "223334411", "rh"));
        employeeMap.put(2L, new Employee(2L, "Peter", "22001543", "informatics"));
        employeeMap.put(3L, new Employee(3L, "Mike", "223334411", "admin"));
    }

    @RequestMapping(value = "/employee/{Id}",produces = { "application/json", "application/xml" }, method = RequestMethod.GET)
    public @ResponseBody Employee getEmployeeById(@PathVariable final Long Id) {
        return employeeMap.get(Id);
    }
}
