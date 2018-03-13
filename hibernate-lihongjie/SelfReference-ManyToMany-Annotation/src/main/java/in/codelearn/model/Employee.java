package in.codelearn.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

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
	

	@ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="EMPLOYEE_COLLEAGUE",
                joinColumns={@JoinColumn(name="EMPLOYEE_ID")},
                inverseJoinColumns={@JoinColumn(name="COLLEAGUE_ID")})
    private Set<Employee> colleagues = new HashSet<Employee>();

	@ManyToMany(mappedBy="colleagues")
	private Set<Employee> teammates = new HashSet<Employee>();

	public Employee() {
	}

	public Employee(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Set<Employee> getTeammates() {
		return teammates;
	}

	public void setTeammates(Set<Employee> teammates) {
		this.teammates = teammates;
	}

	public Set<Employee> getColleagues() {
		return colleagues;
	}

	public void setColleagues(Set<Employee> colleagues) {
		this.colleagues = colleagues;
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

}
