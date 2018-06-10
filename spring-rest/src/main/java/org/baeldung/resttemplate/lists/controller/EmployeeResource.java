package org.baeldung.resttemplate.lists.controller;

import org.baeldung.resttemplate.lists.dto.Employee;
import org.baeldung.resttemplate.lists.dto.EmployeeListDTO;
import org.baeldung.resttemplate.lists.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeResource
{
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public List<Employee> getEmployees()
    {
        return employeeService.getAllEmployees();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/v2")
    public EmployeeListDTO getEmployeesUsingWrapperClass()
    {
        List<Employee> employees = employeeService.getAllEmployees();
        return new EmployeeListDTO(employees);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/")
    public void addEmployees(@RequestBody List<Employee> employees)
    {
        employeeService.addEmployees(employees);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/v2")
    public void addEmployeesUsingWrapperClass(@RequestBody EmployeeListDTO employeeWrapper)
    {
        employeeService.addEmployees(employeeWrapper.getEmployees());
    }
}
