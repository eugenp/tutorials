package com.baeldung;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.domain.EmployeeRepository;
import com.baeldung.hexagonal.domain.EmployeeService;
import com.baeldung.hexagonal.domain.Input;

@RestController
public class HexagonalExampleRestController {

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Employee registerEmployee(@RequestBody Employee employee) {
        Input restInput = new RestInput(employee);
        EmployeeRepository repository = new InMemoryEmployeeRepository();
        EmployeeService employeeService = new EmployeeService(restInput, repository);
        Employee createdEmployee = employeeService.registerEmployee();
        return createdEmployee;
    }

}
