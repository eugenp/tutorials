package com.example.dto;

public class PersonDTO {
    private String name;
    private int age;

    // Employee-specific
    private String department;
    private double salary;

    // Customer-specific
    private String customerId;
    private String tier;

    // Type to decide subclass: "employee" or "customer"
    private String type;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getTier() { return tier; }
    public void setTier(String tier) { this.tier = tier; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}




