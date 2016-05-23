package com.baeldung;

import java.util.Comparator;

//Collector and Collectors Example
public class Employee implements Comparator<Employee> {

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

	@Override
	public int compare(Employee o1, Employee o2) {
		return o1.getSalary() > o2.getSalary() ? 1 : o1.getSalary() < o2.getSalary() ? -1 : 0;
	}

}
