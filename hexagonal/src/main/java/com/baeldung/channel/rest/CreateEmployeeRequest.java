package com.baeldung.channel.rest;

public class CreateEmployeeRequest {

    private String name;
    private String role;
    private long salary;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public long getSalary() {
        return salary;
    }
    public void setSalary(long salary) {
        this.salary = salary;
    }
    @Override
    public String toString() {
        return "CreateEmployeeRequest [name=" + name + ", role=" + role + ", salary=" + salary + "]";
    }
}