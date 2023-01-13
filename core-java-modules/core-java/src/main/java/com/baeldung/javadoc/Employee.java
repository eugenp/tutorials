package com.baeldung.javadoc;

public class Employee {

    private Department department;

    public Employee( Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

//    Shallow copy
    public Employee shallowClone(){
        return new Employee(this.department);
    }

//    Deep copy
    public Employee deepClone(){
        Department department = new Department(this.department.getName());
        Employee employee = new Employee(department);
        return employee;
    }

}
