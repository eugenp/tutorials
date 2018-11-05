package com.baeldung.hexagonal.domain;

import java.util.Optional;

import com.baeldung.hexagonal.database.EmployeeRepository;
import com.baeldung.hexagonal.database.InMemoryRepository;



public class EmployeeService {
	private final EmployeeRepository repository;
	 
	  /**
	   * Constructor
	   */
	  public EmployeeService() {
	    this.repository = new InMemoryRepository();
	  }

	  /**
	   * Insert new employee
	   */
	  public Optional<EmployeeId> insertEmployee(Employee employee) {
	  
	    Optional<EmployeeId> optional = repository.save(employee);
	    if (optional.isPresent()) {
	      
	    }
	    return optional;
	  }

	  /**
	   * Check if employee present
	   */
	  public Optional<Employee> retrieveEmployee(EmployeeId employeeId) {
		  
		    Optional<Employee> optional = repository.findById(employeeId);
		    if (optional.isPresent()) {
		      
		    }
		    return optional;
		  }


}
