package com.baeldung.hexagonal.domain;

import javax.persistence.*;

@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role;
 
    @Column(nullable = false)
    private long salary;
    
    public Employee(){

    }
    public Employee(Integer id, String name, String role, long salary) {
        this.id =   id;
        this.name = name;
        this.role = role;
        this.salary = salary;
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
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public long getSalary() {
        return salary;
    }
    public void setSalary(long salary) {
        this.salary = salary;
    }

}