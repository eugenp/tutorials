package com.baeldung.azure.functions.blob.entity;

public class Employee {
    private String id;
    private String name;
    private String department;
    private String sex;

    public Employee(String id, String name, String department, String sex) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
