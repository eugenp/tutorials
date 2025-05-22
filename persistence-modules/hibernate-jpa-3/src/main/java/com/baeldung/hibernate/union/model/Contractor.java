package com.baeldung.hibernate.union.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Contractor {

    @Id
    private Long id;
    private String name;
    private boolean enabled;

    public Contractor() {
    }

    public Contractor(Long id, String name) {
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
