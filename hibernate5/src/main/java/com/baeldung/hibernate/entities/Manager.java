package com.baeldung.hibernate.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Manager {
    @Id
    long id;
    String employeeNumber;
    String name;
    @ManyToOne    
    Department department;
    
    public Manager(String name, String employeeNumber, Department department) {
        this.name = name;
        this.employeeNumber = employeeNumber;
        this.department = department;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getEmployeeNumber() {
        return employeeNumber;
    }
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
}
