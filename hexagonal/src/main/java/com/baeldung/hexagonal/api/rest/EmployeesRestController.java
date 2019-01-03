package com.baeldung.hexagonal.api.rest;

import com.baeldung.hexagonal.api.EmployeeApi;
import com.baeldung.hexagonal.model.Employee;
import com.baeldung.hexagonal.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Profile("api-rest")
public class EmployeesRestController implements EmployeeApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeesRestController.class) ;

    @Autowired
    private EmployeeService employeeService;

    @Override
    @PostMapping
    public Employee create(String nameFirst, String nameLast) {
        LOGGER.info("create({}, {})", nameFirst, nameLast) ;
        return employeeService.create(nameFirst, nameLast) ;
    }

    @Override
    @GetMapping
    public List<Employee> findAll() {
        LOGGER.info("findAll()") ;
        return employeeService.findAll() ;
    }
}
