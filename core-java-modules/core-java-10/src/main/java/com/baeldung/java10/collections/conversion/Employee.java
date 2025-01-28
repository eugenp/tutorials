package com.baeldung.java10.collections.conversion;

public class Employee implements Comparable<Employee> {
    private int employeeId;
    private String employeeName;

    Employee(int employeeId, String employeeName) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    @Override
    public String toString() {
        return employeeId + " " + employeeName;
    }

    @Override
    public int compareTo(Employee o) {
        if (this.employeeId == o.employeeId) {
            return 0;
        } else if (this.employeeId < o.employeeId) {
            return 1;
        } else {
            return -1;
        }
    }
}
