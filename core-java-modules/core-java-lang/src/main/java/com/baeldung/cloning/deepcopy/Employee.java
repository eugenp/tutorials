package com.baeldung.cloning.deepcopy;

public class Employee implements Cloneable {
    private int employeeId;
    private String employeeName;
    private Department department;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee(int employeeId, String employeeName, Department department) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Employee clonedEmployee = (Employee) super.clone();
        Department clonedDepartment = (Department)clonedEmployee.getDepartment().clone();
        clonedEmployee.setDepartment(clonedDepartment);
        return clonedEmployee;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", department=" + department +
                '}';
    }
}
