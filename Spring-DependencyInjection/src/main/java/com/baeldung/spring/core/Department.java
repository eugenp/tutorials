package com.baeldung.spring.core;

import org.springframework.stereotype.Component;

@Component
public class Department {

	private String departmentName;

	private String departmentId;

	public Department(String departmentName, String departmentNumber) {
		super();
		this.departmentName = departmentName;
		this.departmentId = departmentNumber;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	@Override
	public String toString() {
		return "Department [departmentName=" + departmentName + ", departmentNumber=" + departmentId + "]";
	}

}
