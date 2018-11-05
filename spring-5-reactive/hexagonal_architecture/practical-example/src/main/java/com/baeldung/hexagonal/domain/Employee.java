package com.baeldung.hexagonal.domain;

public class Employee {
	 private EmployeeId id;
	  private final EmployeeDetails employeeDetails;

	  /**
	   * Constructor.
	   */
	  public Employee(EmployeeId id, EmployeeDetails details) {
	    this.id = id;
	    employeeDetails = details;
	  }

	  /**
	   * @return employee details
	   */
	  public EmployeeDetails getEmployeeDetails() {
	    return employeeDetails;
	  }
	  
	  
	  /**
	   * @return id
	   */
	  public EmployeeId getId() {
	    return id;
	  }

	  /**
	   * set id
	   */
	  public void setId(EmployeeId id) {
	    this.id = id;
	  }

	  @Override
	  public String toString() {
	    return employeeDetails.toString();
	  }
}
