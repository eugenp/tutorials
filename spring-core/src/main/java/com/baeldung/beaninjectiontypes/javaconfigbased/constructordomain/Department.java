package com.baeldung.beaninjectiontypes.javaconfigbased.constructordomain;

public class Department {
	private String departmentName;

	public Department(String dname) {
		this.departmentName = dname;

	}

	@Override
	public String toString() {
		return "Department: " + departmentName;
	}

}
