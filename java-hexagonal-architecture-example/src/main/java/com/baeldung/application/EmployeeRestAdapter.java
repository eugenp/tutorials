package com.baeldung.application;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.domain.Employee;
import com.baeldung.domain.EmployeeServicePort;
import com.baeldung.exception.EmployeeNotFoundException;

@RestController
public class EmployeeRestAdapter {

  private EmployeeServicePort employeeServicePort;

  public EmployeeRestAdapter(EmployeeServicePort employeeServicePort) {
    this.employeeServicePort = employeeServicePort;
  }

  @GetMapping
  public List<Employee> getAllEmployees() {
    return employeeServicePort.getAll();
  }

  @GetMapping ("{employeeId}")
  public Employee getById(@PathVariable String employeeId) throws EmployeeNotFoundException {
    return employeeServicePort.get(employeeId);
  }

  @PostMapping
  public Employee create(@RequestBody Employee employee) {
    return employeeServicePort.create(employee);
  }

  @PutMapping
  public Employee update(@RequestBody Employee employee) {
    return employeeServicePort.update(employee);
  }
}
