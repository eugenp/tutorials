package com.baeldung.hibernate.union.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Researcher {

    @Id
    private Long id;
    private String name;
    private boolean active;

    public Researcher() {
    }

    public Researcher(Long id, String name) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
