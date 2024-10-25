package com.baeldung.jakarta;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Employee.byDepartment", query = "FROM Employee WHERE department = :department", resultClass = Employee.class)
public class Employee {

    @Id
    private Long id;

    private String fullName;

    private String department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Employee() {
    }

    public Employee(Long id, String fullName, String department) {
        this.id = id;
        this.fullName = fullName;
        this.department = department;
    }

}