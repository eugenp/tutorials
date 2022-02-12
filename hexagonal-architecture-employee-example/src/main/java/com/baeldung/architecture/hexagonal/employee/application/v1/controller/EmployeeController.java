package com.baeldung.architecture.hexagonal.employee.application.v1.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.architecture.hexagonal.employee.domain.dto.Employee;
import com.baeldung.architecture.hexagonal.employee.domain.exceptions.EmployeeException;
import com.baeldung.architecture.hexagonal.employee.domain.service.EmployeeService;

@RestController
@RequestMapping("/v1/hexagonal/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("{employeeId}")
	public ResponseEntity<Employee> getEmployeeDetail(@PathVariable("employeeId") int employeeId) {
		Employee employee = null;
		try {
			employee = employeeService.findById(employeeId);
			if(employee == null) {
				return new ResponseEntity<Employee>(employee, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		} catch (EmployeeException e) {
			return new ResponseEntity<Employee>(employee, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping
	public ResponseEntity<Employee> getEmployeeDetail(@RequestBody Map<String, String> requestBody) {
		String firstName = requestBody.get("firstName");
		String lastName = requestBody.get("lastName");
		Employee employee = employeeService.addEmployee(firstName, lastName);
		return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
	}
}
