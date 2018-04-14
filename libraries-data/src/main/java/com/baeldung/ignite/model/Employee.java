package com.baeldung.ignite.model;


public class Employee {

    private Integer id;
    private String name;
    private boolean isEmployed;

    public Employee(Integer id, String name, boolean isEmployed) {
        this.id = id;
        this.name = name;
        this.isEmployed = isEmployed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmployed() {
        return isEmployed;
    }

    public void setEmployed(boolean employed) {
        isEmployed = employed;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isEmployed=" + isEmployed +
                '}';
    }
}
