package com.baeldung.persistancecontext.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private Long id;
    private String name;
    private String role;

    public User() {

    }

    public User(Long id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

}
