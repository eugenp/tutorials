package com.baeldung.shallowdeepcopy;

class Employee implements Cloneable {
    private String firstName;
    private String lastName;
    private EmployeeInfo employeeInfo;

    public Employee(String firstName, String lastName, EmployeeInfo employeeInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeInfo = employeeInfo;
    }

    public Employee(Employee originalEmployee) {
        this.firstName = originalEmployee.firstName;
        this.lastName = originalEmployee.lastName;
        this.employeeInfo = originalEmployee.employeeInfo;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public EmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(EmployeeInfo employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
