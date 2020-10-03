package com.baeldung.spring.jdbc;

import java.util.Objects;

public class Employee {
    private int id;
    private String name;
    private String position;
    private double salary;

    public Employee() {
    }

    public Employee(int id, String name, double salary, String position) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, position, salary);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Employee other = (Employee) obj;
        return id == other.id && Objects.equals(name, other.name) && Objects.equals(position, other.position) && Double.doubleToLongBits(salary) == Double.doubleToLongBits(other.salary);
    }

}
