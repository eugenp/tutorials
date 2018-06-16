package com.baeldung.model;

public class Employee {
	
	private int id;
    private String name;
    private String department;
    private long salary;
	
    public Employee(int id, String name, String department, long salary) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.salary = salary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}
	
    public void setId(int id) {
		this.id = id;
	}
	
    public String getName() {
		return name;
	}
	
    public void setName(String name) {
		this.name = name;
	}
	
    public String getDepartment() {
		return department;
	}
	
    public void setDepartment(String department) {
		this.department = department;
	}
	
    public long getSalary() {
		return salary;
	}
	
    public void setSalary(long salary) {
		this.salary = salary;
	}
	
}
