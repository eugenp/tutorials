package com.baeldung.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("employeeControllerWithSetterBasedDI")
public class EmployeeControllerWithSetterBasedDI {
    private DepartmentService departmentService;

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public String getEmployeeDetails() {
        return "Employee: A, Department: " + departmentService.getDepartment();
    }
}
