package com.baeldung.hexagonal.database;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.domain.EmployeeId;


public class InMemoryRepository implements EmployeeRepository {
	private static Map<EmployeeId, Employee> employees = new HashMap<>();

	  @Override
	  public Optional<Employee> findById(EmployeeId id) {
		 
		  Employee employee = employees.get(id);
	    if (employee == null) {
	      return Optional.empty();
	    } else {
	      return Optional.of(employee);
	    }
	  }

	  @Override
	  public Optional<EmployeeId> save(Employee employee) {
		  EmployeeId id = new EmployeeId();
	    employees.put(id, employee);
	    return Optional.of(id);
	  }

}
