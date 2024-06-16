package com.baeldung.deepcopydraft;

import java.io.Serializable;

public class Employee implements Cloneable, Serializable {

    public int id;
    public String name;
    public Company company;

    public Employee(int id, String name, Company company) {
        this.id = id;
        this.name = name;
        this.company = new Company(company.getName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Employee clone() {
        try {
            Employee clone = (Employee) super.clone();
            clone.setCompany(clone.getCompany().clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            return new Employee(0, "", new Company(""));
        }
    }
}
