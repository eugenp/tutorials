package com.baeldung.hibernate.entities;

import javax.persistence.*;

@Entity
public class DeptEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String employeeNumber;

    private String designation;

    private String name;

    @ManyToOne
    private Department department;
    
    public DeptEmployee(String name, String employeeNumber, Department department) {
        this.name = name;
        this.employeeNumber = employeeNumber;
        this.department = department;
    }
    
    public DeptEmployee(String name, String employeeNumber, String designation, Department department) {
        super();
        this.name = name;
        this.employeeNumber = employeeNumber;
        this.designation = designation;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
