package in.codelearn.model;

import java.util.HashSet;
import java.util.Set;

public class Employee {

	private Long employeeId;
	private String firstname;
	private String lastname;

	private Set<Meeting> meetings = new HashSet<Meeting>();
	
	public Employee() {
	}

	public Employee(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	@Override
	public boolean equals(Object emp) {
		if(emp instanceof Employee) {
			Employee employee = (Employee)emp;
			
			if(this.firstname.equals(employee.getFirstname()) &&
					this.lastname.equals(employee.getLastname()))
				return true;
		}

		return false;
	}
	@Override
	public int hashCode() {
	
		return this.firstname.hashCode() + this.lastname.hashCode();
	}
	
	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Set<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(Set<Meeting> meetings) {
		this.meetings = meetings;
	}
	
}
