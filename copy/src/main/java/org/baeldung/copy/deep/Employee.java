package org.baeldung.copy.deep;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Employee implements Cloneable {

    private String employeeId;
    private String name;
    private Department department;

    public Employee(Employee employee) {
        this.employeeId = employee.getEmployeeId();
        this.name = employee.getName();
        this.department = new Department(employee.getDepartment());
    }

    @Override
    public Employee clone() throws CloneNotSupportedException {
        Employee employee = (Employee) super.clone();
        employee.department = department.clone();
        return employee;
    }
}
