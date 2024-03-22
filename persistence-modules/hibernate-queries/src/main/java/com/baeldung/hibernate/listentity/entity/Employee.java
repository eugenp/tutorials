package com.baeldung.hibernate.listentity.entity;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "findAllEmployees", query = "SELECT e FROM Employee e")
@NamedQuery(name = "findEmployeesByDepartment", query = "SELECT e FROM Employee e WHERE e.department = :department ORDER BY e.lastName ASC")
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    private String lastName;

    private String department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
