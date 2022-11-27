package com.baeldung.objectcopy;

public class Employee {
    private String employeeId;

    public Employee(String employeeId){
        this.employeeId=employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

}
