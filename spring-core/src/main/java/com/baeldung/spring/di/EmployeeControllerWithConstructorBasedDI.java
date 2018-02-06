package com.baeldung.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("employeeControllerWithConstructorBasedDI")
public class EmployeeControllerWithConstructorBasedDI {

    private DepartmentService departmentService;

    @Autowired
    public EmployeeControllerWithConstructorBasedDI(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public String getEmployeeDetails() {
        return "Employee: A, Department: " + departmentService.getDepartment();
    }
}
