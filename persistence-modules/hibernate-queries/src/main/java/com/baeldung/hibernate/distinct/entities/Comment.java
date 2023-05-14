package com.baeldung.hibernate.distinct.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String text;

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", text='" + text + '\'' + '}';
    }
}
