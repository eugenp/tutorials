/**
 * 
 */
package com.baeldung.architecture.hexagonal.employee.v1.controller;

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

import com.baeldung.architecture.hexagonal.employee.dto.Employee;
import com.baeldung.architecture.hexagonal.employee.repository.EmployeeRepository;

/**
 * Rest Controller, A Primary Adapter.
 */
@RestController
@RequestMapping("/v1/hexagonal/employees")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	
	@GetMapping("{employeeId}")
	public ResponseEntity<Employee> getEmployeeDetail(@PathVariable("employeeId") int employeeId) {
		Employee employee = employeeRepository.findById(employeeId);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Employee> getEmployeeDetail(@RequestBody Map<String, String> payload) {
		Employee employee = employeeRepository.addEmployee(payload.get("firstName"), payload.get("lastName"));
		return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
	}
}
