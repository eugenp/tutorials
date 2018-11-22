package com.baeldung.hexagonal.domain;

import java.io.Serializable;

public class Employee implements Serializable {

    private String lastName;
    private String firstName;
    private Integer employeeId;

    public Employee(String lastName, String firstName) {
        super();
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Employee() {
		super();
	}

	public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Employee [" + (lastName != null ? "lastName=" + lastName + ", " : "") + (firstName != null ? "firstName=" + firstName + ", " : "") + (employeeId != null ? "employeeId=" + employeeId : "") + "]";
    }

}
