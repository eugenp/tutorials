package com.baeldung.hexagonal.architecture.core;

import java.time.LocalDate;
import java.util.List;

public class BirthdayService {
	
	private EmployeeStore employeeStore;

	public BirthdayService(EmployeeStore employeeStore) {
		this.employeeStore = employeeStore;
	}

	public void sendCoupons() {
		LocalDate today = LocalDate.now();
		List<Employee> employees = employeeStore.getEmployees();
		employees.stream().filter(emp -> emp.getDob().getMonth().equals(today.getMonth()))
				.forEach(System.out::println);
	}

	
}
