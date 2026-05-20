package com.baeldung.hibernate.entitygraph.model;

import jakarta.persistence.Entity;

@Entity
public class Moderator extends User {

    private String department;

    public Moderator() {
    }

    public Moderator(String name, String email, String department) {
        super(name, email);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
