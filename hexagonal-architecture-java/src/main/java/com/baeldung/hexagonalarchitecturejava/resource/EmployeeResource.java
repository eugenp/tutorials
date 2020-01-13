package com.baeldung.hexagonalarchitecturejava.resource;

import com.baeldung.hexagonalarchitecturejava.domain.Employee;
import com.baeldung.hexagonalarchitecturejava.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeResource
{
    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<Employee> getAllEmployee()
    {
        return employeeService.getAllEmployee();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Employee getEmployee(@PathVariable("id") Long id )
    {
        return employeeService.findEmployeeById(id);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Employee updateEmployee(@RequestBody Employee employee) throws Exception {
        return employeeService.update(employee);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteEmployee(@PathVariable("id") Long id )
    {
        employeeService.deleteById(id);
    }

}
