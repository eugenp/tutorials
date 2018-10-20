package com.baeldung.hexagonal.architecture.core;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BirthdayService {

    private EmployeeStore employeeStore;

    public BirthdayService(EmployeeStore employeeStore) {
        this.employeeStore = employeeStore;
    }

    public List<Employee> findMonthlyBirthdayEmployees() {
        LocalDate today = LocalDate.now();
        List<Employee> employees = employeeStore.getEmployees();
        return employees.stream()
            .filter(emp -> emp.getDob()
                .getMonth()
                .equals(today.getMonth()))
            .collect(Collectors.toList());
    }

}
