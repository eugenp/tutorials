package com.baeldung.toon;

public class Employee {

    private String name;
    private int age;
    private String department;
    private int salary;

    public Employee() {
    }

    public Employee(String name, int age, String department, int salary) {
        this.name = name;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }

    // standard getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }
}
