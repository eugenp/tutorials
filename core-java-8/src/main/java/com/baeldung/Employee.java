package com.baeldung;

//Collector and Collectors Example
public class Employee {

	private String name;
	private long salary;
	private int deptId;
	private int empId;

	public Employee() {
	}

	public Employee(final int empId, final String name, final long salary, final int deptId) {
		super();
		this.name = name;
		this.salary = salary;
		this.deptId = deptId;
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}
}
