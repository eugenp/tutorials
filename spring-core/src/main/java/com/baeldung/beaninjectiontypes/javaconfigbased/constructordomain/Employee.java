package com.baeldung.beaninjectiontypes.javaconfigbased.constructordomain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Employee {
	private Department dName;
	private Type type;

	@Autowired
	public Employee(Department dName, Type type) {
		this.dName = dName;
		this.type = type;
	}

	public Employee() {
	}

	@Override
	public String toString() {
		return String.format("Employee: %s Type: %s", dName, type);
	}
}
