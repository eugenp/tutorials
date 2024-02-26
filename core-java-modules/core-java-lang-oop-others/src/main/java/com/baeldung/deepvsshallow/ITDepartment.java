package com.baeldung.deepvsshallow;

public class ITDepartment implements Cloneable {

    private String name;

    private Employee employee;

    public ITDepartment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ITDepartment clone() throws CloneNotSupportedException {
        return (ITDepartment) super.clone();
    }

    @Override
    public String toString() {
        return "ITDepartment{" + "name='" + name + '\'' + '}';
    }
}
