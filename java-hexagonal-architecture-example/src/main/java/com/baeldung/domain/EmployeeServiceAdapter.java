package com.baeldung.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.exception.EmployeeNotFoundException;

@Service
public class EmployeeServiceAdapter implements EmployeeServicePort {

  private EmployeeRepositoryPort employeeRepositoryPort;

  @Autowired
  public EmployeeServiceAdapter(
      EmployeeRepositoryPort employeeRepositoryPort) {
    this.employeeRepositoryPort = employeeRepositoryPort;
  }

  @Override
  public List<Employee> getAll() {
    return employeeRepositoryPort.findAll();
  }

  @Override
  public Employee get(String employeeId) throws EmployeeNotFoundException {
    Optional<Employee> employee = employeeRepositoryPort.findById(employeeId);
    if (employee.isPresent()) {
      return employee.get();
    }
    throw new EmployeeNotFoundException();
  }

  @Override
  public Employee create(Employee employee) {
    return employeeRepositoryPort.save(employee);
  }

  @Override
  public Employee update(Employee employee) {
    return employeeRepositoryPort.save(employee);
  }
}
