package com.baeldung.spring.jpa.guide.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Publishers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String location;
    private int journals;

    public Publishers() {
    }

    public Publishers(String name, String location, int journals) {
        this.name = name;
        this.location = location;
        this.journals = journals;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getJournals() {
        return journals;
    }

    public void setJournals(int journals) {
        this.journals = journals;
    }
}
