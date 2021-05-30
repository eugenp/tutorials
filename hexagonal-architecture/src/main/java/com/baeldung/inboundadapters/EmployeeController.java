package com.baeldung.inboundadapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.domain.Employee;
import com.baeldung.inboundports.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public void createEmployeeInfo(@RequestBody Employee emp) {
		employeeService.createEmployeeInfo(emp);
	}

	@GetMapping("/{empId}")
	public Employee getEmployeeInfo(@PathVariable Integer empId) {
		return employeeService.getEmployeeInfo(empId);
	}

	@GetMapping
	public List<Employee> getEmployeesInfo() {
		return employeeService.getAllEmployeesInfo();
	}
}