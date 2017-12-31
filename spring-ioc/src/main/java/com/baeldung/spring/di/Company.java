package com.baeldung.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.spring.di.service.Department;
import com.baeldung.spring.di.service.Employee;

@Component
public class Company {

	private Employee employee;
	private Department department;

	@Autowired
	public Company(Employee employee) {
		this.employee = employee;
	}

	public void showEmployeeInfo() {
		employee.showEmployeeInfo();
	}

	@Autowired
	public void setDepartment(Department department) {
		this.department = department;
	}

	public void showDepartmentInfo() {
		department.showDepartmentInfo();
	}
}
