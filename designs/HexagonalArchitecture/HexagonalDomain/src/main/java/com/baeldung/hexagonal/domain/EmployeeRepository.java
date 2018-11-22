package com.baeldung.hexagonal.domain;

import java.util.Optional;

public interface EmployeeRepository {

	Employee saveEmployee(Employee employee);
}
