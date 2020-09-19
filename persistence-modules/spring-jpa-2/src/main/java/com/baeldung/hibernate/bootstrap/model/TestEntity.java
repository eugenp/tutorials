package com.baeldung.hibernate.bootstrap.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TestEntity {

    private int id;

    private String description;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
