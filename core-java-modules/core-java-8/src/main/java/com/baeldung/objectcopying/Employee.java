package com.baeldung.objectcopying;

public class Employee {
    private String name;
    private Department department;

    public Employee(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }    

    public Employee shallowCopy() {
        return new Employee(name, department);
    }

    public Employee deepCopy() {
        return new Employee(name, new Department(department));
    }    
}