package com.baeldung.hexagonal.database;

import java.util.Optional;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.domain.EmployeeId;

/**
 * 
 * Interface for accessing employees in database
 *
 */
public interface EmployeeRepository {
	 /**
	   * Find Employee by id
	   */
	  Optional<Employee> findById(EmployeeId id);

	  /**
	   * Save Employee
	   */
	  Optional<EmployeeId> save(Employee employee);
}
