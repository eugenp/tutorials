package com.baeldung.ddd.hexagonal.architecture.input.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.ddd.hexagonal.architecture.domain.Employee;
import com.baeldung.ddd.hexagonal.architecture.input.port.EmployeeService;

@RestController
public class EmployeeRestController {
    
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeRestController.class); 
    EmployeeService empService;
    
    @Autowired
    public EmployeeRestController(EmployeeService empService) {
        this.empService = empService;
    }
    
    @PostMapping("/employee")
    public String saveEmployeeDetails(@RequestBody Employee e) {
        LOG.info("going to save the employee details");
        String id = empService.saveEmployeeDetails(e);
        return "Employee details saved with id: " +id;
    }
    
    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable String id) {
        LOG.info("going to get the employee details for id: "+id);
        return empService.getEmployeeDetailsById(id);
    }
    
    @DeleteMapping("/employee/{id}")
    public void deleteEmployeeById(@PathVariable String id) {
        LOG.info("going to delete the employee details for id: "+id);
        empService.deleteEmployeeDetailsById(id);
    }
    
}
