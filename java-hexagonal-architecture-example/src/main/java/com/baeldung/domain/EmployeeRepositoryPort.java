package com.baeldung.domain;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepositoryPort {

  List<Employee> findAll();

  Optional<Employee> findById(String employeeId);

  Employee save(Employee employee);
}
