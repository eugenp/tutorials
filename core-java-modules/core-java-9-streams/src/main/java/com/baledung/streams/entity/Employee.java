package com.baledung.streams.entity;

public class Employee {
    private String name;
    private int salary;
    private String department;
    private String sex;

    public Employee(String name, int salary, String department, String sex) {
        this.name = name;
        this.salary = salary;
        this.department = department;
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" + "name='" + name + '\'' + ", salary=" + salary + ", department='" + department + '\'' +
            ", sex='" + sex + '\'' + '}';
    }
}
