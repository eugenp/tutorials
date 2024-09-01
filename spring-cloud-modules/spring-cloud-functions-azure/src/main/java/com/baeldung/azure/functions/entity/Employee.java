package com.baeldung.azure.functions.entity;

public class Employee {
    private String name;
    private String department;
    private String city;
    private Integer salary;

    public Employee(String name, String department, String city, Integer salary) {
        this.name = name;
        this.department = department;
        this.city = city;
        this.salary = salary;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
