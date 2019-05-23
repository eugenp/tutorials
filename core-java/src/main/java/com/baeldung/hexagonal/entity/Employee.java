package com.baeldung.hexagonal.entity;

public class Employee {

	private Long empCode;
	private String name;

	public Employee(Long empCode, String name) {
		super();
		this.empCode = empCode;
		this.name = name;
	}

	public Long getEmpCode() {
		return empCode;
	}

	public void setEmpCode(Long empCode) {
		this.empCode = empCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Employee [empCode=" + empCode + ", name=" + name + "]";
	}

}
