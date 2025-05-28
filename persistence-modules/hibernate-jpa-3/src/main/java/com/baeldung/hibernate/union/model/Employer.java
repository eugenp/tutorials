package com.baeldung.hibernate.union.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Employer {

    @Id
    private Long id;
    private String name;
    private Integer department;

    public Employer() {
    }

    public Employer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }
}
