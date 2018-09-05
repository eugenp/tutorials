package com.baeldung.hibernate.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Department {
    @Id
    long id;
    String name;
    @OneToMany(mappedBy="department")
    List<Manager> employees;

    public Department(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Manager> getEmployees() {
        return employees;
    }
    public void setEmployees(List<Manager> employees) {
        this.employees = employees;
    }
}
