package com.stackify.models;

public class Employee {

    private String email;
    private String name;

    private double salary;

    public Employee() {
    }

    public Employee(String email, String name, double salary) {
        this.email = email;
        this.name = name;
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

}
