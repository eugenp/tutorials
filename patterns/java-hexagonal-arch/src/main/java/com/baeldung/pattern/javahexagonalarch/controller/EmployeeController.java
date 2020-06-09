package com.baeldung.pattern.javahexagonalarch.controller;

import com.baeldung.pattern.javahexagonalarch.domain.Employee;
import com.baeldung.pattern.javahexagonalarch.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

@RestController @RequestMapping("/app") public class EmployeeController {

        @Autowired private EmployeeService employeeService;

        @GetMapping("/employees") public ArrayList<Employee> getAllEmployees() {
                return employeeService.getAllEmployees();
        }

        @PostMapping("/employees") public void addEmployee(Employee e) {
                employeeService.addEmployee(e);
        }

        @GetMapping("/employees/{id}") public Employee getEmployeesById(@PathParam(value = "id") int id) {
                return employeeService.getEmployeesById(id);
        }

}
