package com.baeldung.hibernate.entitygraph.model;

import jakarta.persistence.Entity;

@Entity
public class Author extends User {

    private String bio;

    public Author() {
    }

    public Author(String name, String email, String bio) {
        super(name,email);
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
