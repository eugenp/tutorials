package com.baeldung.hexagonal.domain;

public class EmployeeDetails {
	  private final String firstName;
	  private final String lastName;

	  /**
	   * Constructor.
	   */
	  public EmployeeDetails(String fName, String lName) {
	    firstName = fName;
	    lastName = lName;
	  }

	  /**
	   * @return first name
	   */
	  public String getFirstName() {
	    return firstName;
	  }
	  
	  /**
	   * @return last name
	   */
	  public String getLastName() {
	    return lastName;
	  }
	  

	  @Override
	  public String toString() {
	    return "EmployeeDetails{" + "firstName='" + firstName + '\''
	        + ", lastName='" + lastName + '\''
	        + '}';
	  }
}
