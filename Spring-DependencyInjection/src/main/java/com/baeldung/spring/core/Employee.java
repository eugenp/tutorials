package com.baeldung.spring.core;

import org.springframework.stereotype.Component;

@Component
public class Employee {

	private Department department;

	public Employee(Department department) {
		super();
		this.department = department;
	}

	public Department getDepartment() {
		return department;
	}

	@Override
	public String toString() {
		return "Employee [department=" + department + "]";
	}
}
