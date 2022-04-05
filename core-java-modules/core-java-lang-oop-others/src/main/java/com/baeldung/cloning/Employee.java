package com.baeldung.cloning;

class Employee {
    private int id;
    private Department department;

    // standard getters/setters/constructors/toString
    public Employee() {
    }

    public Employee(int id, Department department) {
        this.id = id;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", department=" + department + '}';
    }
}
