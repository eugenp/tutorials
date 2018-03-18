package com.baeldung.beaninjection;

public class Employee {

    private final String name;
    private final Department department;

    public Employee(String name, Department department) {
        super();
        this.name = name;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", department=" + department + "]";
    }

}
