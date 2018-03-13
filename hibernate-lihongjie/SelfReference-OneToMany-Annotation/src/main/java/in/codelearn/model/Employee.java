package in.codelearn.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE")
public class Employee {
	
	@Id
	@Column(name="EMPLOYEE_ID")
	@GeneratedValue
	private Long employeeId;
	
	@Column(name="FIRSTNAME")
	private String firstname;
	
	@Column(name="LASTNAME")
	private String lastname;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="manager_id")
	private Employee manager;

	@OneToMany(mappedBy="manager")
	private Set<Employee> subordinates = new HashSet<Employee>();

	public Employee() {
	}

	public Employee(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Set<Employee> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(Set<Employee> subordinates) {
		this.subordinates = subordinates;
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

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}
	
}
